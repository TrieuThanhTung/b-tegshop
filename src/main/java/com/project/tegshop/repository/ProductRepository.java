package com.project.tegshop.repository;

import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);

    @Query("select p from Product p where p.category = :category order by p.productId desc limit 20 offset :page")
    Optional<List<Product>> getProductByCategory(@Param("category") Category category,
                                                 @Param("page") int page);

    @Query("select p from Product p where p.category = :category order by p.price asc limit 20 offset :page")
    Optional<List<Product>> getProductByCategoryAndPriceASC(@Param("category") Category category,
                                                            @Param("page") int page);

    @Query("select p from Product p where p.category = :category order by p.price desc limit 20 offset :page")
    Optional<List<Product>> getProductByCategoryAndPriceDESC(@Param("category") Category category,
                                                             @Param("page") int page);

    @Query("select p from Product p where p.category = :category order by p.productName asc limit 20 offset :page")
    Optional<List<Product>> getProductByCategoryAndProductName(@Param("category") Category category,
                                                               @Param("page") int page);

    @Query("select p from Product p where p.user.userId = :id")
    Optional<List<Product>> findByUserId(@Param("id") Integer id);

    @Query("select p from Product p order by p.productId DESC limit 10")
    Optional<List<Product>> findByTrending();

}
