package com.project.tegshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    @NotNull
    private String description;
    @NotNull
    private String street;
    @NotNull
    private String district;
    @NotNull
    private String province;
}
