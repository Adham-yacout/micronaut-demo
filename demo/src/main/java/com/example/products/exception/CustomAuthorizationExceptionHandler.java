package com.example.products.exception;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler;
import io.micronaut.security.authentication.WwwAuthenticateChallengeProvider;
import io.micronaut.security.config.RedirectConfiguration;
import io.micronaut.security.config.RedirectService;
import io.micronaut.security.errors.PriorToLoginPersistence;
import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Singleton
@Replaces(DefaultAuthorizationExceptionHandler.class)
public class CustomAuthorizationExceptionHandler
        extends DefaultAuthorizationExceptionHandler {

    public CustomAuthorizationExceptionHandler(
            ErrorResponseProcessor<?> errorResponseProcessor,
            RedirectConfiguration redirectConfiguration,
            RedirectService redirectService,
            List<WwwAuthenticateChallengeProvider<HttpRequest<?>>> challengeProviders,
            @Nullable PriorToLoginPersistence priorToLoginPersistence) {

        super(
                errorResponseProcessor,
                redirectConfiguration,
                redirectService,
                challengeProviders,
                priorToLoginPersistence
        );
    }

    @Override
    public MutableHttpResponse<?> handle(
            HttpRequest request,
            AuthorizationException exception) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                401,
                "Unauthorized",
                "Authentication token is missing or invalid",
                request.getPath()
        );

        return HttpResponse.unauthorized().body(error);
    }
}