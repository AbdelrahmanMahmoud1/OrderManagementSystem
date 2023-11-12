package com.giza.purshasingmanagement;

import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Utils {
    /** Checking order validity through products **/
    static public void checkOrderValidity(OrderDTO order) {
        if (order == null || order.getProducts() == null) {
//            logger.error("Order with no products");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with no products");
        }
        order.getProducts().forEach(product -> {
            if (product.getQuantity() <= 0 || product.getPrice() <= 0) {
//                logger.error("Invalid price/quantity");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid price/quantity");
            }
            else if (product.getName().isBlank()){
//                logger.error("Invalid product name");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product name");
            }
        });
    }
}
