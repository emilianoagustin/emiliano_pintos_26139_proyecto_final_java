package com.techlab.productcrud.dto;

import jakarta.validation.constraints.Positive;

public class AddCartItemRequestDTO {

  private Long productId;
  
  @Positive(message = "Quantity has to be a positive number")
  private Integer quantity;

  public AddCartItemRequestDTO() {}

  public Long getProductId() {
    return this.productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
