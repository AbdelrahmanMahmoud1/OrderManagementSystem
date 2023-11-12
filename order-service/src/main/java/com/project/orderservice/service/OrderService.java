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
import org.springframework.http.*;
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

    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed


    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;


    public OrderService(OrderRepository orderRepository, WebClient webClient, KafkaTemplate kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void placeOrder(OrderRequest orderRequest, String auth){//recieving the order request from the client to the controller 1
        // controller passing the order request to the order service 2
             Order order= new Order();
             order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::maptodto)
                .toList();//mapping the order request to order object

        order.setProducts(orderLineItems);

        //Call inventory service and place the order if it is in stock




        // Check product availability by making a request to the inventory service
        // TODO: 11/12/2023 why send entity in request?
        Order orderWithAvailability = checkProductAvailabilityAndCreateOrder(order, auth);
        System.out.println(orderWithAvailability);
        System.out.println(orderWithAvailability.getOrderNumber());
        System.out.println(orderWithAvailability.getProducts().get(0).getQuantity());

        // Save the order to the repo (assuming the order is valid)
        orderRepository.save(orderWithAvailability);
     //kafka sends orderPlacedEvent as a message to notification topic

        System.out.println(confirmPurchase(orderWithAvailability, auth));
    }


    private OrderLineItems maptodto(OrderLineItemsDto orderLineItemsDto) {
        // TODO: 11/12/2023 use builder *nice to have*
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setName(orderLineItemsDto.getName());
        return orderLineItems;

    }

    private Order checkProductAvailabilityAndCreateOrder(Order order, String auth) {
        List<OrderLineItems> checkedProducts;
        checkedProducts = getAvailableProductsFromInventory(order.getProducts(), auth);
        order.setProducts(checkedProducts);

        return order;
    }
    private ProductResponse getProductAvailabilityById(String name) {
        // TODO: 11/12/2023 why use webclient? why not resttemplate or feignclient?
        // TODO: 11/12/2023 extract url to a variable or constants class
        return webClient.get()
                .uri("http://localhost:8765/products/product/{name}", name)
                .retrieve()
                .bodyToMono(ProductResponse.class).block(); // Return false if there is an error (e.g., product not found or out of stock)
    }

    private List<OrderLineItems> getAvailableProductsFromInventory(List<OrderLineItems> products, String auth) {



        RestTemplate response = new RestTemplate();
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, auth);
        HttpEntity<List<OrderLineItems>> postRequest = new HttpEntity<>(products, authHeader);

        InventoryResponse inventoryResponse = response.exchange("http://localhost:8765/products/selling", HttpMethod.POST, postRequest,InventoryResponse.class).getBody();

        if (inventoryResponse != null)
            return inventoryResponse.getProducts();

        return new ArrayList<>();
    }

    @Transactional
    public HttpStatusCode confirmPurchase(Order orderWithAvailability, String auth) {


        // TODO: 11/12/2023 why resttemplate and webclient in the same class?
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, auth);
        HttpEntity<Order> postRequest = new HttpEntity<>(orderWithAvailability, authHeader);
        // TODO: 11/12/2023 extract url to a variable or constants class
        HttpStatus submitOrderResponse = restTemplate.exchange("http://localhost:8765/selling/submit-order", HttpMethod.POST, postRequest,HttpStatus.class).getBody();
        // TODO: 11/12/2023 you can transform it to be like that         return Objects.requireNonNullElse(submitOrderResponse, HttpStatus.NOT_FOUND);
        if (submitOrderResponse != null){
            return submitOrderResponse;
        }else {
            return  HttpStatus.NOT_FOUND;
        }
        // TODO: 11/12/2023 if its commented its not needed rule of thumb

//        ResponseEntity<SubmitOrderResponse> response = new RestTemplate().postForEntity(
//                "http://localhost:8765/selling/submit-order",
//                orderWithAvailability,
//                SubmitOrderResponse.class
//        );
//
//        if (response.getBody() != null)
//            return response.getBody();
//        else
//            return new SubmitOrderResponse();
    }
}



