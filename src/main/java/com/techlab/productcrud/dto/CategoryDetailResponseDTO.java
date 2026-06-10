package com.techlab.productcrud.dto;

import java.util.List;

public class CategoryDetailResponseDTO {

  private Long id;
  private String name;
  private String description;
  private List<ProductSummaryDTO> products;

  public CategoryDetailResponseDTO() {}

  public CategoryDetailResponseDTO(Long id, String name, String description, List<ProductSummaryDTO> products) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.products = products;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public List<ProductSummaryDTO> getProducts() {
    return this.products;
  }
}
