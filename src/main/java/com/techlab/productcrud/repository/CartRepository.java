package com.techlab.productcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.productcrud.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
  Cart findByUserId(Long userId);
}
