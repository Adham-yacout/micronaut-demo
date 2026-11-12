package com.example.products.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class GlobalExceptionHandler {

    @Error(global = true, exception = ProductNotFoundException.class)
    public HttpResponse<ErrorResponse> handleNotFound(
            HttpRequest<?> request,
            ProductNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                exception.getMessage(),
                request.getPath()
        );

        return HttpResponse.status(HttpStatus.NOT_FOUND).body(error);
    }


    @Error(global = true, exception = ConstraintViolationException.class)
    public HttpResponse<ErrorResponse> handleValidation(
            HttpRequest<?> request,
            ConstraintViolationException exception) {

        String message = exception.getConstraintViolations()
                .stream()
                .map(violation ->
                        violation.getMessage() + ": " + "should not be empty" )
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                message,
                request.getPath()
        );

        return HttpResponse.badRequest(error);
    }
//    @Error(global = true, exception = Exception.class)
//    public HttpResponse<ErrorResponse> handleGeneral(
//            HttpRequest<?> request,
//            Exception exception) {
//
//        ErrorResponse error = new ErrorResponse(
//                500,
//                "Internal Server Error",
//                "An unexpected error occurred",
//                request.getPath()
//        );
//
//        return HttpResponse.serverError(error);
//    }
}