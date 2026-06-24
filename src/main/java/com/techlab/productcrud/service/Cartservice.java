package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.Cart;
import com.techlab.productcrud.entity.CartItem;
import com.techlab.productcrud.entity.Product;
import com.techlab.productcrud.entity.User;

import com.techlab.productcrud.repository.CartRepository;
import com.techlab.productcrud.repository.CartItemRepository;
import com.techlab.productcrud.repository.ProductRepository;
import com.techlab.productcrud.repository.UserRepository;

import com.techlab.productcrud.dto.CartResponseDTO;
import com.techlab.productcrud.dto.CartItemResponseDTO;
import com.techlab.productcrud.dto.AddCartItemRequestDTO;

import com.techlab.productcrud.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Cartservice {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public Cartservice(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  // DTOs MAPPERS //

  private CartResponseDTO mapToCartResponseDTO(Cart cart) {
    List<CartItemResponseDTO> items = cart.getCartItems().stream().map(this::mapToCartItemResponseDTO).toList();
    Integer totalItems = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
    Double totalPrice = cart.getCartItems().stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    CartResponseDTO cartResponseDTO = new CartResponseDTO(cart.getId(), cart.getUser().getId(), items, totalItems, totalPrice);
    return cartResponseDTO;
  }

  private CartItemResponseDTO mapToCartItemResponseDTO(CartItem cartItem) {
    Product product = cartItem.getProduct();
    CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO(product.getId(), product.getName(), product.getPrice(), cartItem.getQuantity(), (product.getPrice() * cartItem.getQuantity()));

    return cartItemResponseDTO;
  }

  // CRUD METHODS //

  public CartResponseDTO addItemToCart(Long userId, AddCartItemRequestDTO addCartItemRequestDTO) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

    Cart cart = cartRepository.findByUserId(userId);

    if(cart == null) {
      cart = new Cart();
      cart.setUser(user);
      cart = cartRepository.save(cart);
    }

    Long productId = addCartItemRequestDTO.getProductId();
    Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

    Optional<CartItem> cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

    if(cartItem.isEmpty()) {
      CartItem newCartItem = new CartItem();
      newCartItem.setCart(cart);
      newCartItem.setProduct(product);
      newCartItem.setQuantity(addCartItemRequestDTO.getQuantity());
      cartItemRepository.save(newCartItem);
    } else {
      CartItem existingItem = cartItem.get();
      existingItem.setQuantity(existingItem.getQuantity() + addCartItemRequestDTO.getQuantity());
      cartItemRepository.save(existingItem);
    }

    Long cartId = cart.getId();
    cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));

    return mapToCartResponseDTO(cart);
  }
}
