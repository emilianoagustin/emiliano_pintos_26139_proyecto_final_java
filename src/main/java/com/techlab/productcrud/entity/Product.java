package com.techlab.productcrud.entity;

import jakarta.persistence.*;

import java.lang.Double;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private Double price;
  private Integer stock;
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  public Product(){}

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public Double getPrice() {
    return this.price;
  }
  
  public void setPrice(Double price) {
    this.price = price;
  }
  
  public Integer getStock() {
    return this.stock;
  }
  
  public void setStock(Integer stock) {
    this.stock = stock;
  }
  
  public String getImage() {
    return this.imageUrl;
  }

  public void setImage(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
