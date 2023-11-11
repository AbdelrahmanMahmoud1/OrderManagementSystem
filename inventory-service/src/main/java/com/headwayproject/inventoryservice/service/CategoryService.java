package com.headwayproject.inventoryservice.service;

import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    private final CategoryRepository categoryRepository;

    public List<Category> findAll(){
      return  categoryRepository.findAll();
    }
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String addCategory(Category category){
        // TODO: 11/11/2023 why have return when its not used?
        categoryRepository.save(category);
        return "Category successfully added";

    }
    public Category findById(int id){
       Category category = categoryRepository.findById(id);
       return category;
    }
    public Category findByName(String name){
        Category category = categoryRepository.findByName(name);
        return category;
    }

    public String delete (int id){
        // TODO: 11/11/2023 why have return when its not used?

        Category category = categoryRepository.findById(id);
        if(category!=null){
            categoryRepository.deleteById(id);
            return "Deleted Successfully";
        }
        else {
            return "Category not found";
        }

    }

}
