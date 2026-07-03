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
public class CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  // HELPERS //

  private User getUserOrThrow(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
  }
  
  private Product getProductOrThrow(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
  }

  private Cart getCartOrThrow(Long userId) {
    Cart cart = cartRepository.findByUserId(userId);

    if(cart == null) {
      throw new ResourceNotFoundException("Cart not found for user ID: " + userId);
    }

    return cart;
  }

  private Cart getOrCreateCart(User user) {
    Cart cart = cartRepository.findByUserId(user.getId());

    if(cart == null) {
      cart = new Cart();
      cart.setUser(user);
      cart = cartRepository.save(cart);
    }

    return cart;
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
    User user = getUserOrThrow(userId);

    Cart cart = getOrCreateCart(user);

    Long productId = addCartItemRequestDTO.getProductId();
    Product product = getProductOrThrow(productId);
    
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
  
  public CartResponseDTO getCart(Long userId) {
    getUserOrThrow(userId);
    Cart cart = getCartOrThrow(userId);

    return mapToCartResponseDTO(cart);
  }
  
  public CartItemResponseDTO deleteItem(Long userId, Long productId) {
    getUserOrThrow(userId);
    getProductOrThrow(productId);
    Cart cart = getCartOrThrow(userId);

    CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found."));
    CartItemResponseDTO cartItemResponseDTO = mapToCartItemResponseDTO(cartItem);
    
    cartItemRepository.delete(cartItem);
    
    return cartItemResponseDTO;
  }
  
  public CartResponseDTO emptyCart(Long userId) {
    getUserOrThrow(userId);
    Cart cart = getCartOrThrow(userId);

    cartItemRepository.deleteAll(cart.getCartItems());

    cart = getCartOrThrow(userId);

    return mapToCartResponseDTO(cart);
  }
}
