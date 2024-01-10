package com.project.tegshop.repository;

import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);

    Optional<List<Product>> findByCategory(Category category);

    @Query("select p from Product p where p.user.userId = :id")
    Optional<List<Product>> findByUserId(@Param("id") Integer id);
}
