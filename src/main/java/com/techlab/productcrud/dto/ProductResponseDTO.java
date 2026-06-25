package com.techlab.productcrud.dto;

public class ProductResponseDTO {
  
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer stock;
  private String imageUrl;

  private Long categoryId;
  private String categoryName;

  public ProductResponseDTO(){}

  public ProductResponseDTO(Long id, String name, String description, Double price, Integer stock, String imageUrl, Long categoryId, String categoryName){

    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.imageUrl = imageUrl;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public Double getPrice() {
    return this.price;
  }

  public Integer getStock() {
    return this.stock;
  }

  public String getImage() {
    return this.imageUrl;
  }

  public Long getCategoryId() {
    return this.categoryId;
  }

  public String getCategoryName() {
    return this.categoryName;
  }
}