package br.com.joaogabriel.payload.request;

import jakarta.validation.constraints.NotBlank;

public record RequestPost(
        @NotBlank(message = "The field description cannot be empty, blank or null.")
        String description) {
}
