package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.requests.LoginRequest;
import tasks.manager.api.requests.RegisterRequest;
import tasks.manager.api.responses.JwtAuthenticationResponse;
import tasks.manager.api.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }
}
