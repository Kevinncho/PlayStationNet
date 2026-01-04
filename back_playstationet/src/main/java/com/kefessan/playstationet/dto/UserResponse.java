package com.kefessan.playstationet.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.kefessan.playstationet.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private Set<String> roles;

}
