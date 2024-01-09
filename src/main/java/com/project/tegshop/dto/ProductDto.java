package com.project.tegshop.dto;

import com.project.tegshop.model.Category;
import com.project.tegshop.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    @NotBlank
    private String productName;
    @NotNull
    private Integer price;
    @NotBlank
    private String description;
    @NotBlank
    private String manufacturer;
    @NotNull
    @Min(0)
    @Max(100)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Category category;
    @NotNull
    private List<String> images;
}
