package com.techlab.productcrud.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.lang.Double;

public class ProductRequestDTO {
  
  @NotBlank(message = "Product name is required")
  private String name;

  @NotBlank(message = "Product description is required")
  private String description;

  @NotNull(message = "Price cannot be empty")
  @DecimalMax(value = "9999999.99", message = "Price exceeds maximum limit")
  @Digits(integer = 7, fraction = 2, message = "Price format must match up to 7 digits and 2 decimals")
  @Positive(message = "Price has to be a positive number")
  private Double price;
  
  @NotNull(message = "Category ID cannot be empty")
  private Long categoryId;

  public ProductRequestDTO(){}

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

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Long getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}
