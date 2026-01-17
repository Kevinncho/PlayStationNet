package com.kefessan.playstationet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.LoginRequest;
import com.kefessan.playstationet.dto.LoginResponse;
import com.kefessan.playstationet.dto.RegisterRequest;
import com.kefessan.playstationet.security.JwtUtil;
import com.kefessan.playstationet.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager,
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                request.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(token);
    }

@PostMapping("/register")
public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {

    System.out.println("➡️ Registro request: " + request.getUsername() + ", " + request.getEmail());

    // 1️⃣ Validar y crear usuario
    userService.register(request); // Aquí puede lanzar tus excepciones

    // 2️⃣ Generar token sin autenticar manualmente
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String token = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new LoginResponse(token));
}
}