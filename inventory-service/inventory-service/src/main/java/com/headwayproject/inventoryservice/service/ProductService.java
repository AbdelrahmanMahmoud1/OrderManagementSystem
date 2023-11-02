package com.headwayproject.inventoryservice.service;

import com.headwayproject.inventoryservice.entity.Product;
import com.headwayproject.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;



    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public String addProduct(Product product){
        productRepository.save(product);
        return "product added successfully";
    }
    public List<Product> getAllProducts(){
       List<Product> products = productRepository.findAll();
       return products;
    }

    public Product getProductById(int id){
        Product product = productRepository.findById(id);
        return product;
    }

    public String deleteById(int id){
      Product product = productRepository.findById(id);
       if(product!=null){
           productRepository.deleteById(id);
           return "Deleted successfuly";
       }
       else {
           return "product not found";
       }
    }

    public String editById(int id,Product product){
       Product productToEdit = productRepository.findById(id);
        if(productToEdit!=null){
            productToEdit.setCategory(product.getCategory());
            productToEdit.setPrice(product.getPrice());
            productToEdit.setName(product.getName());
            productToEdit.setQuantity(product.getQuantity());
            productRepository.save(productToEdit);
            return "Edited successfuly";
        }
        else {
            return "Product not found";
        }




    }

    public Product getProductByName(String name) {
        Product product = productRepository.findByName(name);
        return product;
    }

    public void increaseQuantityWhenBuying(Product product) {
        Product productToEdit = productRepository.findByName(product.getName());
        if (productToEdit != null) {
            productToEdit.setCategory(productToEdit.getCategory());
            productToEdit.setPrice(product.getPrice());
            productToEdit.setName(product.getName());
            productToEdit.setQuantity(product.getQuantity()+productToEdit.getQuantity());
            productRepository.save(productToEdit);

        }
    }
    public void deductQuantityWhenSelling(Product product) {
        Product productToEdit = productRepository.findByName(product.getName());
        if (productToEdit != null) {
            productToEdit.setCategory(productToEdit.getCategory());
            productToEdit.setPrice(product.getPrice());
            productToEdit.setName(product.getName());
            productToEdit.setQuantity(productToEdit.getQuantity()-product.getQuantity());
            productRepository.save(productToEdit);

        }
    }
}


