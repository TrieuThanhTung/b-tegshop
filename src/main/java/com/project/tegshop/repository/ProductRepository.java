package com.project.tegshop.repository;

import com.project.tegshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("select p from Product p where p.productName = :product")
    Optional<Product> findByProductName(String productName);
}
