package com.kefessan.playstationet.dto;

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
public class UserUpdateRequest {

    private String username;
    private String email;
    private String role; // puede ser ROLE_USER o ROLE_ADMIN
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String password;


    // getters y setters
}
