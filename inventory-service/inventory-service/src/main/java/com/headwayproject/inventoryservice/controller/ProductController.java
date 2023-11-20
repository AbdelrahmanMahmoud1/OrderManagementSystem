package com.headwayproject.inventoryservice.controller;



import com.headwayproject.inventoryservice.dto.ProductDto;

import com.headwayproject.inventoryservice.response.InventoryResponse;
import com.headwayproject.inventoryservice.service.CategoryService;
import com.headwayproject.inventoryservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
   CategoryService categoryService;


    @GetMapping()
    public List<ProductDto> getAll(){
       return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable int id){
      return productService.getProductById(id);
    }
    @GetMapping("/product/{name}")
    public ProductDto findProductByName(@PathVariable String name){
       return productService.getProductByName(name);

    }

    @PostMapping("/purchased")
    public ResponseEntity<InventoryResponse> addPurchasedItems(@RequestBody List<ProductDto> products){
        InventoryResponse response = new InventoryResponse();
        response.setProducts(productService.addNewlyPurchasedItems(products));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/selling")
    public List<ProductDto> checkItemsAvailability(@RequestBody List<ProductDto> products){
        return productService.checkAvailabilityOfOrderedItems(products);
    }
    @PostMapping
    public String saveProduct(@RequestBody ProductDto product){
        productService.addProduct(product);
        return "Product is added";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteById(id);
        return "Deleted Successfully";
    }
    @PutMapping("/{id}")
    public String editProduct(@PathVariable int id,@RequestBody ProductDto product){
        productService.editById(id,product);
        return "Edited Successfully";
    }
}
