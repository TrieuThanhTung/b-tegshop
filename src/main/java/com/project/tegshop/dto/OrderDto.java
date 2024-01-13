package com.project.tegshop.dto;

import com.project.tegshop.model.Address;
import com.project.tegshop.model.CartItem;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    @NotNull
    private String description;
    @NotNull
    private Integer addressId;
//    @NotNull
//    private List<Integer> cartItemlist;
}
