package com.kefessan.playstationet.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.RegisterRequest;
import com.kefessan.playstationet.dto.UserResponse;
import com.kefessan.playstationet.enumeration.ERole;
import com.kefessan.playstationet.exception.EmailAlreadyExistsException;
import com.kefessan.playstationet.exception.UserAlreadyExistsException;
import com.kefessan.playstationet.model.Role;
import com.kefessan.playstationet.model.User;
import com.kefessan.playstationet.repository.RoleRepository;
import com.kefessan.playstationet.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                "Error: El nombre de usuario '" + request.getUsername() + "' ya existe"
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                "Error: El correo '" + request.getEmail() + "' ya está registrado"
            );
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setIsAdmin(false);

        Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER.name())
                .orElseThrow(() -> new RuntimeException("Error: El rol " + ERole.ROLE_USER.name() + " no se encuentra en la base de datos."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        
        user.setRoles(roles);
        userRepository.save(user);
    }
 public List<UserResponse> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(user -> UserResponse.builder()
                    .id(user.getIdUser())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getName())
                    .lastName(user.getLastName())
                    .roles(
                        user.getRoles()
                            .stream()
                            .map(role -> role.getRoleName())
                            .collect(Collectors.toSet())
                    )
                    .build()
            )
            .collect(Collectors.toList());
}
}