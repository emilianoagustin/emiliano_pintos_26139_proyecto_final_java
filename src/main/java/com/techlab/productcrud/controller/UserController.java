package com.techlab.productcrud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.techlab.productcrud.service.UserService;
import com.techlab.productcrud.dto.UserRequestDTO;
import com.techlab.productcrud.dto.UserResponseDTO;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    return userService.createUser(userRequestDTO);
  }

  @GetMapping("/{id}")
  public UserResponseDTO getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @GetMapping
  public List<UserResponseDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @PutMapping("/{id}")
  public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
    return userService.updateUser(id, userRequestDTO);
  }

  @DeleteMapping("/{id}")
  public UserResponseDTO deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }
}
