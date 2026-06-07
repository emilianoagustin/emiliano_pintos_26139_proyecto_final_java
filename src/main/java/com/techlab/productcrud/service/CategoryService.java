package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.repository.CategoryRepository;
import com.techlab.productcrud.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Category getCategory(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
  }
}
