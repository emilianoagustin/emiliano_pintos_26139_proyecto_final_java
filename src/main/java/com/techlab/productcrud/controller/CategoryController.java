package com.techlab.productcrud.controller;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @PostMapping
  public Category createCategory(@RequestBody Category category) {
      return categoryRepository.save(category);
  }

  @GetMapping
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
}
