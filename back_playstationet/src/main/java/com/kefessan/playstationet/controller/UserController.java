package com.kefessan.playstationet.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.UserResponse;
import com.kefessan.playstationet.dto.UserUpdateRequest;
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
            @PutMapping("/me")
        public ResponseEntity<UserResponse> updateMyUser(
                Authentication authentication,
                @RequestBody UserUpdateRequest request) {

            String username = authentication.getName();

            UserResponse response = userService.updateMyUser(username, request);
            return ResponseEntity.ok(response);
        }
        @DeleteMapping("/me")
        public ResponseEntity<Void> deleteMyUser(Authentication authentication) {

            String username = authentication.getName();
            userService.deleteMyUser(username);

            return ResponseEntity.noContent().build(); // 204
        }
}
