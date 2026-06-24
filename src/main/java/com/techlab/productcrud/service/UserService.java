package com.techlab.productcrud.service;

import com.techlab.productcrud.entity.User;
import com.techlab.productcrud.entity.Cart;
import com.techlab.productcrud.repository.UserRepository;
import com.techlab.productcrud.repository.CartRepository;
import com.techlab.productcrud.repository.CartItemRepository;
import com.techlab.productcrud.dto.UserRequestDTO;
import com.techlab.productcrud.dto.UserResponseDTO;
import com.techlab.productcrud.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  public UserService (UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
    this.userRepository = userRepository;
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
  }

  // DTOs MAPPERS //

  private UserResponseDTO mapToResponseDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
  }

  private User mapToEntity(UserRequestDTO userRequestDTO) {
    User user = new User();
    user.setFirstName(userRequestDTO.getFirstName());
    user.setLastName(userRequestDTO.getLastName());
    user.setEmail(userRequestDTO.getEmail());

    return user;
  }

  // CRUD METHODS //

  public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
    User user = mapToEntity(userRequestDTO);
    User savedUser = userRepository.save(user);

    return mapToResponseDTO(savedUser);
  }

  public List<UserResponseDTO> getAllUsers(){
    List<User> users = userRepository.findAll();
    List<UserResponseDTO> usersResponseList = users.stream().map(this::mapToResponseDTO).toList();

    return usersResponseList;
  }

  public UserResponseDTO getUser(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    return mapToResponseDTO(user);
  }

  public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    user.setFirstName(userRequestDTO.getFirstName());
    user.setLastName(userRequestDTO.getLastName());
    user.setEmail(userRequestDTO.getEmail());
    
    return mapToResponseDTO(userRepository.save(user));
  }
  
  public UserResponseDTO deleteUser(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    Cart cart = cartRepository.findByUserId(id);

    if(cart != null) {
      user.setCart(null);
      cartItemRepository.deleteAll(cart.getCartItems());
      cartRepository.delete(cart);
    }

    userRepository.delete(user);

    return mapToResponseDTO(user);
  }
}
