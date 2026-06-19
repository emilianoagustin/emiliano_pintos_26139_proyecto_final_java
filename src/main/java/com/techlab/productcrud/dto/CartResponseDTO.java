package com.techlab.productcrud.dto;

import java.util.List;

public class CartResponseDTO {
  
  private Long cartId;
  private Long userId;
  private List<CartItemResponseDTO> items;
  private Integer totalItems;
  private Double totalPrice;

  public CartResponseDTO() {}

  public CartResponseDTO(Long cartId, Long userId, List<CartItemResponseDTO> items, Integer totalItems, Double totalPrice) {
    this.cartId = cartId;
    this.userId = userId;
    this.items = items;
    this.totalItems = totalItems;
    this.totalPrice = totalPrice;
  }

  public Long getCartId() {
    return this.cartId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public List<CartItemResponseDTO> getItems() {
    return this.items;
  }

  public Integer getTotalItems() {
    return this.totalItems;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }
}
