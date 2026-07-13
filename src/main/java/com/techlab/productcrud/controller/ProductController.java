package com.techlab.productcrud.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
  public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
    ProductResponseDTO product = productService.createProduct(productRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(product);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
    ProductResponseDTO product = productService.getProduct(id);
    return ResponseEntity.ok(product);
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String name, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice, @PageableDefault(size = 10) Pageable pageable) {
    Page<ProductResponseDTO> products = productService.getAllProducts(categoryId, name, minPrice, maxPrice, pageable);
    return ResponseEntity.ok(products);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
    ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable Long id) {
    ProductResponseDTO deletedProduct = productService.deleteProduct(id);
    return ResponseEntity.ok(deletedProduct);
  }
}
