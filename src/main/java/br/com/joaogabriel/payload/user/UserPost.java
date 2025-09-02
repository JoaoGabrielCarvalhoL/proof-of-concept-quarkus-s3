package br.com.joaogabriel.payload.user;

import jakarta.validation.constraints.NotBlank;

public record UserPost(
        @NotBlank(message = "The field username cannot be empty, blank or null.")
        String username) {
}
