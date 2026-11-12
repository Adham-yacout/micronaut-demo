package com.example.auth;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.token.generator.TokenGenerator;

import java.time.Instant;
import java.util.Map;

@Controller("/auth")
public class AuthController {

    private final TokenGenerator tokenGenerator;

    public AuthController(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Post("/token")
    public Map<String, String> generateToken() {


        long expirationTime = Instant.now()
                .plusSeconds(1 * 60)
                .getEpochSecond();

        Map<String, Object> claims = Map.of(
                "sub", "test-user",
                "roles", new String[]{"USER"},
                "exp", expirationTime
        );

        String token = tokenGenerator.generateToken(claims)
                .orElseThrow(() ->
                        new IllegalStateException("Could not generate JWT"));

        return Map.of("access_token", token);
    }
}
