package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.entity.Product;
import com.techlab.productcrud.repository.CategoryRepository;
import com.techlab.productcrud.repository.ProductRepository;
import com.techlab.productcrud.exception.ResourceNotFoundException;
import com.techlab.productcrud.dto.ProductRequestDTO;
import com.techlab.productcrud.dto.ProductResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  // DTOs MAPPERS //

  private ProductResponseDTO mapToResponseDTO(Product product) {
    return new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId(), product.getCategory().getName());
  }

  private Product mapToEntity(ProductRequestDTO productRequestDTO) {
    Product product = new Product();
    product.setName(productRequestDTO.getName());
    product.setDescription(productRequestDTO.getDescription());
    product.setPrice(productRequestDTO.getPrice());

    Long categoryId = productRequestDTO.getCategoryId();
    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId)
    );
    product.setCategory(category);

    return product;
  }

  // CRUD METHODS //

  public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
    Product savedProduct = productRepository.save(mapToEntity(productRequestDTO));
    
    return mapToResponseDTO(savedProduct);
  }

  public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
    Page<Product> productPage = productRepository.findAll(pageable);
    Page<ProductResponseDTO> productResponseDTO = productPage.map(this::mapToResponseDTO);
    
    return productResponseDTO;
  }

  public ProductResponseDTO getProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    return mapToResponseDTO(product);
  }
  
  public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    
    product.setName(productRequestDTO.getName());
    product.setDescription(productRequestDTO.getDescription());
    product.setPrice(productRequestDTO.getPrice());
    
    Long categoryId = productRequestDTO.getCategoryId();
    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId)
    );
    product.setCategory(category);
  
    return mapToResponseDTO(productRepository.save(product));
  }

  public ProductResponseDTO deleteProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    productRepository.delete(product);
    
    return mapToResponseDTO(product);
  }
}
