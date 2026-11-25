package edu.lakshaned.presenceme_backend.controller;

import edu.lakshaned.presenceme_backend.dto.LoginRequest;
import edu.lakshaned.presenceme_backend.dto.LoginResponse;
import edu.lakshaned.presenceme_backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}