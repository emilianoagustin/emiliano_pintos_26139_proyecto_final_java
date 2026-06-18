package com.techlab.productcrud.dto;

public class UserResponseDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;

  public UserResponseDTO(){}

  public UserResponseDTO(Long id, String firstName, String lastName, String email){

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Long getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }
}
