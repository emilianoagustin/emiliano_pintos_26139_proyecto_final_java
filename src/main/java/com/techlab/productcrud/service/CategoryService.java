package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.repository.CategoryRepository;
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
}
