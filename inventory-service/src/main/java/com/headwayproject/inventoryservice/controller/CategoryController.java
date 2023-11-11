package com.headwayproject.inventoryservice.controller;

import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  public final  CategoryService categoryService;

  // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
  // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

  public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll(){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class
       return categoryService.findAll();

    }
    @GetMapping("/{id}")
    public Category findById(@PathVariable int id){
      // TODO: 11/11/2023 never ever return database object in response map it to dto and move logic of that into a service class

      return categoryService.findById(id);
    }

//    @GetMapping("/{name}")
//    public Category findByName(@PathVariable String name){
//        return categoryService.findByName(name);
//    }

    @PostMapping
    public void addCategory(@RequestBody Category category){
      // TODO: 11/11/2023 never ever take as an input database object in request map it to dto and move logic of that into a service class

      categoryService.addCategory(category);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        categoryService.delete(id);

    }


}
