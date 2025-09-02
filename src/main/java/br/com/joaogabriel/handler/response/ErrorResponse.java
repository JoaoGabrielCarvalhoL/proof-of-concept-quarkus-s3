package br.com.joaogabriel.handler.response;

public record ErrorResponse(String title, String description, Integer httpStatus) {
}
