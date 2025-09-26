package org.autoservicio.backendcontratoservicio.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.autoservicio.backendcontratoservicio.model.user.UserModel;
import org.autoservicio.backendcontratoservicio.request.RegisterRequestModel;
import org.autoservicio.backendcontratoservicio.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserModel> register(@RequestBody RegisterRequestModel request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword())
                .map(token -> new AuthResponse(token, "Bearer"));
    }

    // Clase para la respuesta de login
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String token;
        private String type;
    }

    // Clase para el request de login
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
