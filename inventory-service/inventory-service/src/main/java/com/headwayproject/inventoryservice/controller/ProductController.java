package com.headwayproject.inventoryservice.controller;


import com.headwayproject.inventoryservice.entity.Product;
import com.headwayproject.inventoryservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
   private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAll(){
       List<Product> products= productService.getAllProducts();
       return products;

    }

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable int id){
      Product product =  productService.getProductById(id);
      return product;
    }
    @GetMapping("/product/{name}")
    public Product findProductByName(@PathVariable String name){
        Product product =  productService.getProductByName(name);
        return product;
    }

    @GetMapping("/product")
    public List<Product> findProductByName(@RequestBody List<Product> products){
        for (Product value : products) {
            Product product = productService.getProductByName(value.getName());

            if (product != null) {
                int id = product.getId();
                System.out.println(productService.editById(id, value));
                value.setId(productService.getProductByName(value.getName()).getId());
                System.out.println(product.getQuantity());
                System.out.println(id);
            } else {
                productService.addProduct(value);
                value.setId(productService.getProductByName(value.getName()).getId());


            }
        }
        return products;
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteById(id);
    }
    @PutMapping("/{id}")
    public void editProduct(@PathVariable int id,@RequestBody Product product){
        productService.editById(id,product);
    }
}
