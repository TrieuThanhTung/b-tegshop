package com.project.tegshop.dto.resetPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RequestEmail {
    @NotBlank
    @Email(message = "Email must be a well formed email address")
    private String emailId;
}
