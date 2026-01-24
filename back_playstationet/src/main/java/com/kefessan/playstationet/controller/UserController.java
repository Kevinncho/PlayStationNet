package com.kefessan.playstationet.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.UserResponse;
import com.kefessan.playstationet.model.User;
import com.kefessan.playstationet.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyUser(Authentication authentication) {

        String username = authentication.getName();
        return ResponseEntity.ok(
            userService.getUserByUsername(username)
        );
    }
}
