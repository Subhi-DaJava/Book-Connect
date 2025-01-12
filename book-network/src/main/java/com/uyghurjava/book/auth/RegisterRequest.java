package com.uyghurjava.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotEmpty(message = "FirstName is mandatory")
        @NotBlank(message = "FirstName is mandatory")

        String firstName,
        @NotEmpty(message = "LastName is mandatory")
        @NotBlank(message = "LastName is mandatory")
        String lastName,

        @NotEmpty(message = "Email is mandatory")
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotEmpty(message = "Password is mandatory")
        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, message = "Password should be at least 8 characters")
        String password
) {
}
