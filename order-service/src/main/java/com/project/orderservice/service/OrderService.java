package com.project.orderservice.service;

import com.project.orderservice.config.ProductResponse;
import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderLineItems;
import com.project.orderservice.repository.OrderRepository;
import com.project.orderservice.responses.InventoryResponse;
import com.project.orderservice.responses.SubmitOrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient; //calling the webclient bean



    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;


    public OrderService(OrderRepository orderRepository, WebClient webClient, KafkaTemplate kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void placeOrder(OrderRequest orderRequest){//recieving the order request from the client to the controller 1
        // controller passing the order request to the order service 2
             Order order= new Order();
             order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::maptodto)
                .toList();//mapping the order request to order object

        order.setProducts(orderLineItems);

        //Call inventory service and place the order if it is in stock




        // Check product availability by making a request to the inventory service
        Order orderWithAvailability = checkProductAvailabilityAndCreateOrder(order);
        System.out.println(orderWithAvailability);
        System.out.println(orderWithAvailability.getOrderNumber());
        System.out.println(orderWithAvailability.getProducts().get(0).getQuantity());

        // Save the order to the repo (assuming the order is valid)
        orderRepository.save(orderWithAvailability);
      ////////  kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber())); //kafka sends orderPlacedEvent as a message to notification topic

        System.out.println(confirmPurchase(orderWithAvailability));
    }


    private OrderLineItems maptodto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setName(orderLineItemsDto.getName());
        return orderLineItems;

    }

    private Order checkProductAvailabilityAndCreateOrder(Order order) {
        List<OrderLineItems> checkedProducts;
        checkedProducts = getAvailableProductsFromInventory(order.getProducts());
        order.setProducts(checkedProducts);

        return order;
    }
    private ProductResponse getProductAvailabilityById(String name) {
        return webClient.get()
                .uri("http://localhost:8080/products/product/{name}", name)
                .retrieve()
                .bodyToMono(ProductResponse.class).block(); // Return false if there is an error (e.g., product not found or out of stock)
    }

    private List<OrderLineItems> getAvailableProductsFromInventory(List<OrderLineItems> products) {
        ResponseEntity<InventoryResponse> response = new RestTemplate().postForEntity(
                "http://localhost:8080/products/selling",
                products,
                InventoryResponse.class
        );
        if (response.getBody() != null)
            return response.getBody().getProducts();
        return new ArrayList<>();
    }

    @Transactional
    private SubmitOrderResponse confirmPurchase(Order orderWithAvailability) {
        ResponseEntity<SubmitOrderResponse> response = new RestTemplate().postForEntity(
                "http://localhost:4560/selling/submit-order",
                orderWithAvailability,
                SubmitOrderResponse.class
        );

        if (response.getBody() != null)
            return response.getBody();
        else
            return new SubmitOrderResponse();
    }
}



