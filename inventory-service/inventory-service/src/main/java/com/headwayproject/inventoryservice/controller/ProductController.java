package com.headwayproject.inventoryservice.controller;


import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.entity.Product;
import com.headwayproject.inventoryservice.service.CategoryService;
import com.headwayproject.inventoryservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
   private final ProductService productService;
   private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
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

    @PostMapping("/purchased")
    public List<Product> addPurchasedItems(@RequestBody List<Product> products){
        for (Product value : products) {
            Product product = productService.getProductByName(value.getName());
            if (product!=null) {
               {
                    int id = product.getId();
                    productService.increaseQuantityWhenBuying(value);
                    value.setId(productService.getProductByName(value.getName()).getId());
                }
            } else {
                Category category = categoryService.findByName(value.getCategory().getName());
                if (category==null){
                    categoryService.addCategory(value.getCategory());
                    productService.addProduct(value);
                    value.setId(productService.getProductByName(value.getName()).getId());


                }
                else {
                    value.setCategory(category);
                    productService.addProduct(value);
                    value.setId(productService.getProductByName(value.getName()).getId());
                }




            }
        }
        return products;
    }

    @PostMapping("/selling")
    public List<Product> checkItemsAvailability(@RequestBody List<Product> products){
        products.stream().forEach(product -> {
            Product productInStock = productService.getProductByName(product.getName());
            if(productInStock.getQuantity()>0){
                productService.deductQuantityWhenSelling(product);
                product.setId(productInStock.getId());
                product.setCategory(productInStock.getCategory());
            }
            else {
                products.remove(product);
            }
        });
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
