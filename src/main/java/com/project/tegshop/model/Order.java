package com.project.tegshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private Long total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;

    @ManyToOne
    @JoinColumn(
            name = "address_id",
            referencedColumnName = "addressId",
            foreignKey = @ForeignKey(name = "FK_ORDER_ADDRESS")
    )
    private Address address;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "FK_ORDER_USER")
    )
    @JsonIgnoreProperties(value = {
            "password",
            "cart",
            "addresses",
            "enabled"
    })
    private UserEntity user;
}
