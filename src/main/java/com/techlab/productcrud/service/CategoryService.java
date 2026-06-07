package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.repository.CategoryRepository;
import com.techlab.productcrud.exception.ResourceNotFoundException;
import com.techlab.productcrud.dto.CategoryRequestDTO;
import com.techlab.productcrud.dto.CategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
    
    Category category = new Category();
    category.setName(categoryRequestDTO.getName());
    category.setDescription(categoryRequestDTO.getDescription());

    Category savedCategory = categoryRepository.save(category);

    return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public CategoryResponseDTO getCategory(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

    return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
  }
}
