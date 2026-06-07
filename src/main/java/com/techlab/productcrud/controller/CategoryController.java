package com.techlab.productcrud.controller;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  public Category createCategory(@Valid @RequestBody Category category) {
      return categoryService.createCategory(category);
  }

  @GetMapping
  public List<Category> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public Category getCategory(@PathVariable Long id) {
      return categoryService.getCategory(id);
  }
  
}
