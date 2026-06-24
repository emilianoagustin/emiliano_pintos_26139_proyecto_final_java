package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Category;
import com.techlab.productcrud.entity.Product;
import com.techlab.productcrud.repository.CategoryRepository;
import com.techlab.productcrud.repository.ProductRepository;
import com.techlab.productcrud.exception.ResourceNotFoundException;
import com.techlab.productcrud.dto.ProductRequestDTO;
import com.techlab.productcrud.dto.ProductResponseDTO;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  private static final Set<String> ALLOWED_SORT_ATTRIBUTES = Set.of("name", "price");

  private static void validatePagination(Pageable pageable) {
    if(pageable.getPageSize() > 40) throw new IllegalArgumentException("Page size cannot exceed 40");
    
    for(Sort.Order order : pageable.getSort()) {
      if(!ALLOWED_SORT_ATTRIBUTES.contains(order.getProperty())) {
        throw new IllegalArgumentException("Sort attribute " + order.getProperty() + " is not allowed.");
      }
    }
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

  public Page<ProductResponseDTO> getAllProducts(Long categoryId, String name, Double minPrice, Double maxPrice, Pageable pageable) {
    validatePagination(pageable);

    boolean hasName = name != null && !name.isBlank();
    boolean hasCategoryId = categoryId != null;
    boolean hasMinPrice = minPrice != null;
    boolean hasMaxPrice = maxPrice != null;

    if(hasMinPrice != hasMaxPrice) {
      throw new IllegalArgumentException("minPrice and maxPrice values are required.");
    }
    if(hasMinPrice && hasMaxPrice && minPrice > maxPrice) {
      throw new IllegalArgumentException("minPrice cannot be greater than maxPrice.");
    }
    if(hasMinPrice && minPrice < 0) {
      throw new IllegalArgumentException("minPrice cannot be a negative number.");
    }
    if(hasMaxPrice && maxPrice > 5000) {
      throw new IllegalArgumentException("maxPrice cannot be greater than $5000.");
    }


    Page<Product> productPage;
    if(hasCategoryId && hasName) {
      productPage = productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name, pageable);
    } else if(!hasCategoryId && hasName) {
      productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
    } else if(hasCategoryId && !hasName) {
      productPage = productRepository.findByCategoryId(categoryId, pageable);
    } else if(hasMinPrice && hasMaxPrice){
      productPage = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }else {
      productPage = productRepository.findAll(pageable);
    }

    Page<ProductResponseDTO> productResponsePage = productPage.map(this::mapToResponseDTO);
    
    return productResponsePage;
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
