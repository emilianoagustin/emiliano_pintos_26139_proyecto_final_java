package com.techlab.productcrud.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.techlab.productcrud.service.ProductService;
import com.techlab.productcrud.dto.ProductResponseDTO;
import com.techlab.productcrud.dto.ProductRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
    return productService.createProduct(productRequestDTO);
  }

  @GetMapping("/{id}")
  public ProductResponseDTO getProduct(@PathVariable Long id) {
    return productService.getProduct(id);
  }

  @GetMapping
  public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
    return productService.getAllProducts(pageable);
  }

  @PutMapping("/{id}")
  public ProductResponseDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
    return productService.updateProduct(id, productRequestDTO);
  }

  @DeleteMapping("/{id}")
  public ProductResponseDTO deleteProduct(@PathVariable Long id) {
    return productService.deleteProduct(id);
  }
}
