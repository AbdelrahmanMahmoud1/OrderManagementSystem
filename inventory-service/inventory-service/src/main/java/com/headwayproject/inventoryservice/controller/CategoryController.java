package com.headwayproject.inventoryservice.controller;

import com.headwayproject.inventoryservice.dto.CategoryDto;
import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping
    public List<CategoryDto> getAll(){
       return categoryService.findAll();

    }
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable int id){
     return categoryService.findById(id);
    }

//    @GetMapping("/{name}")
//    public Category findByName(@PathVariable String name){
//        return categoryService.findByName(name);
//    }

    @PostMapping
    public String addCategory(@RequestBody CategoryDto categoryDto){
        categoryService.addCategory(categoryDto);
        return "Category Added Successfully";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        categoryService.delete(id);
        return "Deleted Successfully";

    }


}
