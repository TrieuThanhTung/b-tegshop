package com.project.tegshop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer quantity;
}
