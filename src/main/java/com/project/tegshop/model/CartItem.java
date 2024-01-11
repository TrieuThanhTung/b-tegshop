package com.project.tegshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer cartItemId;

    @ManyToOne
    @JoinColumn(name = "product_id",
                referencedColumnName = "productId",
                foreignKey = @ForeignKey(name = "FK_CART_ITEM_PRODUCT"))
    @JsonIgnoreProperties(value = {"productId",
            "description",
            "quantity",
            "user"})
    private Product product;
    private Integer quantity;
}
