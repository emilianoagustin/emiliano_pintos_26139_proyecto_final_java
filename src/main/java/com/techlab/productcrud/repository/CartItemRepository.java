package com.techlab.productcrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.productcrud.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByCartAndProductId(Long cartId, Long productId);
}
