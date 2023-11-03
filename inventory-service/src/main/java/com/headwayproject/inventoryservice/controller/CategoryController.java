package com.headwayproject.inventoryservice.controller;

import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  public final  CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll(){
       return categoryService.findAll();

    }
    @GetMapping("/{id}")
    public Category findById(@PathVariable int id){
       return categoryService.findById(id);
    }

//    @GetMapping("/{name}")
//    public Category findByName(@PathVariable String name){
//        return categoryService.findByName(name);
//    }

    @PostMapping
    public void addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        categoryService.delete(id);

    }


}
