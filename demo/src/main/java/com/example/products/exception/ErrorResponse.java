package com.example.products.exception;


import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path
) {}
