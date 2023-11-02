package com.giza.purshasingmanagement.controller.buying;

import com.giza.purshasingmanagement.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/buying")
public class BuyingController {

    private final Logger logger = LoggerFactory.getLogger(BuyingController.class);

    @PostMapping("/buying")
    public String buyItems(@RequestBody Order order) {
        checkOrderValidity(order);
        return "";
    }

    private void checkOrderValidity(Order order) {
        if (order == null || order.getProducts() == null) {
            logger.error("Order with no products");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with no products");
        }
        order.getProducts().forEach(product -> {
            if (product.getQuantity() <= 0 || product.getPrice() <= 0) {
                logger.error("Invalid price/quantity");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid price/quantity");
            }
            else if (product.getName().isBlank()){
                logger.error("Invalid product name");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product name");
            }
        });
    }
}
