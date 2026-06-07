package com.techlab.productcrud.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

  @NotBlank(message = "Category name is required")
  private String name;

  @NotBlank(message = "Category description is required")
  private String description;

  public CategoryRequestDTO(){}

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
