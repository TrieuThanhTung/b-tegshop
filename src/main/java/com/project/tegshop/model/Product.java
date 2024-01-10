package com.project.tegshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(unique = true, nullable = false)
    private String productName;
    private Integer price;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private String manufacturer;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(columnDefinition = "BLOB")
    private List<String> images;
    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "FK_PRODUCT_USER")
    )
    @JsonIgnore
    private UserEntity user;
}
