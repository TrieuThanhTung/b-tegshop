package com.project.tegshop.dto;

import com.project.tegshop.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusOrderDto {
    @NotNull
    private Integer id;
    @NotNull
    private OrderStatus status;
}
