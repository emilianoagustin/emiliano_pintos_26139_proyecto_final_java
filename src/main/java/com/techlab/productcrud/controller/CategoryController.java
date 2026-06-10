package com.techlab.productcrud.controller;

import com.techlab.productcrud.service.CategoryService;
import com.techlab.productcrud.dto.CategoryDetailResponseDTO;
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
  public List<CategoryResponseDTO> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public CategoryResponseDTO getCategory(@PathVariable Long id) {
    return categoryService.getCategory(id);
  }

  @GetMapping("/{id}/details")
  public CategoryDetailResponseDTO getCategoryDetail(@PathVariable Long id) {
    return categoryService.getCategoryDetails(id);
  }

  @PutMapping("/{id}")
  public CategoryResponseDTO updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
    return categoryService.updateCategory(id, categoryRequestDTO);
  }

  @DeleteMapping("/{id}")
  public CategoryResponseDTO deleteCategory(@PathVariable Long id){
    return categoryService.deleteCategory(id);
  }

}
