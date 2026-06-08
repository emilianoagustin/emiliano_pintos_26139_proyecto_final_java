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

  private CategoryResponseDTO mapToResponseDTO(Category category) {
    return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
  }

  private Category mapToEntity(CategoryRequestDTO categoryRequestDTO) {
    Category category = new Category();
    category.setName(categoryRequestDTO.getName());
    category.setDescription(categoryRequestDTO.getDescription());

    return category;
  }

  // CRUD METHODS //

  public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
    Category savedCategory = categoryRepository.save(mapToEntity(categoryRequestDTO));

    return mapToResponseDTO(savedCategory);
  }

  public List<CategoryResponseDTO> getAllCategories() {
    List<Category> categoryList = categoryRepository.findAll();

    List<CategoryResponseDTO> categoryResponseDTOList = categoryList.stream().map(this::mapToResponseDTO).toList();
    return categoryResponseDTOList;
  }

  public CategoryResponseDTO getCategory(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    
    return mapToResponseDTO(category);
  }
  
  public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    category.setName(categoryRequestDTO.getName());
    category.setDescription(categoryRequestDTO.getDescription());
    
    return mapToResponseDTO(categoryRepository.save(category));
  }
  
  public CategoryResponseDTO deleteCategory(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    categoryRepository.delete(category);

    return mapToResponseDTO(category);
  }
}
