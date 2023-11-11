package com.headwayproject.inventoryservice.controller;


import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.entity.Product;
import com.headwayproject.inventoryservice.response.InventoryResponse;
import com.headwayproject.inventoryservice.service.CategoryService;
import com.headwayproject.inventoryservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
   private final ProductService productService;
   private final CategoryService categoryService;
  // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
  // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Product> getAll(){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class

      List<Product> products= productService.getAllProducts();
       return products;

    }

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable int id){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class

      Product product =  productService.getProductById(id);
      return product;
    }
    @GetMapping("/product/{name}")
    public Product findProductByName(@PathVariable String name){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class

      Product product =  productService.getProductByName(name);
        return product;
    }

    @PostMapping("/purchased")
    public ResponseEntity<InventoryResponse> addPurchasedItems(@RequestBody List<Product> products){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class
      // TODO: 11/11/2023 never ever take as an input database object in request map it to dto and move logic of that into a service class

      // TODO: 11/11/2023 move business logic to service class
      for (Product value : products) {
        // TODO: 11/11/2023 why call db here when you already have the object already?
            Product product = productService.getProductByName(value.getName());
            if (product!=null) {
               {
                 // TODO: 11/11/2023 what are you doing with this variable?
                    int id = product.getId();


                    productService.increaseQuantityWhenBuying(value);
                    value.setId(productService.getProductByName(value.getName()).getId());
                    value.setCategory(product.getCategory());
                }
            } else {
              // TODO: 11/11/2023 why call db when the category is already in the object?
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
        InventoryResponse response = new InventoryResponse();
        response.setProducts(products);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/selling")
    public ResponseEntity<InventoryResponse> checkItemsAvailability(@RequestBody List<Product> products){
      // TODO: 11/11/2023 never ever take as an input database object in request map it to dto and move logic of that into a service class

      // TODO: 11/11/2023 move logic to business service class

      products.forEach(product -> {
        // TODO: 11/11/2023 why call db when you already have the object already?
            Product productInStock = productService.getProductByName(product.getName());
            if(productInStock!=null){
                if (productInStock.getQuantity() > 0) {
                    productService.deductQuantityWhenSelling(product);
//                    product.setId(productInStock.getId());
                    product.setCategory(productInStock.getCategory());
                } else {
                    products.remove(product);
                }
            }
        });
        InventoryResponse response =new InventoryResponse();
        response.setProducts(products);
        return ResponseEntity.ok().body(response);
    }


//    @PostMapping("/selling")
//    public ResponseEntity<InventoryResponse> checkItemsAvailability(@RequestBody List<Product> products){
//        products.forEach(product -> {
//            Product productInStock = productService.getProductByName(product.getName());
//            if(productInStock!=null){
//                if (productInStock.getQuantity() > 0) {
//                    productService.deductQuantityWhenSelling(product);
//                    product.setCategory(productInStock.getCategory());




    @PostMapping
    // TODO: 11/11/2023 never ever take as an input database object in request map it to dto and move logic of that into a service class

    public void saveProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteById(id);
    }
    @PutMapping("/{id}")
    // TODO: 11/11/2023 never ever take as an input database object in request map it to dto and move logic of that into a service class
// TODO: 11/11/2023 also only take input the object without id as a separate variable
    public void editProduct(@PathVariable int id,@RequestBody Product product){
        productService.editById(id,product);
    }
}
