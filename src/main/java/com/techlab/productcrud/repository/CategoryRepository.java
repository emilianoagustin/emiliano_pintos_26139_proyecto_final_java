package com.techlab.productcrud.repository;

import com.techlab.productcrud.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
