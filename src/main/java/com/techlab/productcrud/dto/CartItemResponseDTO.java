package com.techlab.productcrud.dto;

public class CartItemResponseDTO {
  
  private Long productId;
  private String productName;
  private Double unitPrice;
  private Integer quantity;
  private Double subtotal;

  public CartItemResponseDTO() {}
  
  public CartItemResponseDTO(Long productId, String productName, Double unitPrice, Integer quantity, Double subtotal) {
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.subtotal = subtotal;
  }

  public Long getProductId() {
    return this.productId;
  }

  public String getProductName() {
    return this.productName;
  }

  public Double getUnitPrice() {
    return this.unitPrice;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

  public Double getSubtotal() {
    return this.subtotal;
  }
}
