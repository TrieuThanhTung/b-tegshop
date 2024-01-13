package com.project.tegshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String emailId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "cartId",
            foreignKey = @ForeignKey(name = "FK_USER_CART")
    )
    private Cart cart;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    private Boolean enabled;
}
