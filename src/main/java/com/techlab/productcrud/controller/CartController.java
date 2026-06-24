package com.techlab.productcrud.controller;

import com.techlab.productcrud.service.CartService;
import com.techlab.productcrud.dto.CartResponseDTO;
import com.techlab.productcrud.dto.CartItemResponseDTO;
import com.techlab.productcrud.dto.AddCartItemRequestDTO;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping("/items")
  public CartResponseDTO addItemToCart(@PathVariable Long userId, @Valid @RequestBody AddCartItemRequestDTO addCartItemRequestDTO) {
    return cartService.addItemToCart(userId, addCartItemRequestDTO);
  }

  @GetMapping
  public CartResponseDTO getCart(@PathVariable Long userId) {
    return cartService.getCart(userId);
  }

  @DeleteMapping("/items/{productId}")
  public CartItemResponseDTO deleteItem(@PathVariable Long userId, @PathVariable Long productId) {
    return cartService.deleteItem(userId, productId);
  }

  @DeleteMapping
  public CartResponseDTO emptyCart(@PathVariable Long userId) {
    return cartService.emptyCart(userId);
  }
}
