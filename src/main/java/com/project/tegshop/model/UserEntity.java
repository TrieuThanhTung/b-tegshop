package com.project.tegshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "cartId",
            foreignKey = @ForeignKey(name = "FK_USER_CART")
    )
    @JsonIgnore
    private Cart cart;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    @JsonIgnore
    private Boolean enabled;
}
