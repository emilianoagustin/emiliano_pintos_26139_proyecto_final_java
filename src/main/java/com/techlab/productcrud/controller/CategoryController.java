package com.techlab.productcrud.controller;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.service.CategoryService;
import com.techlab.productcrud.dto.CategoryRequestDTO;
import com.techlab.productcrud.dto.CategoryResponseDTO;
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
  public CategoryResponseDTO createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
      return categoryService.createCategory(categoryRequestDTO);
  }

  @GetMapping
  public List<Category> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public CategoryResponseDTO getCategory(@PathVariable Long id) {
      return categoryService.getCategory(id);
  }
  
}
