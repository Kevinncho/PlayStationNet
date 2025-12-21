package com.kefessan.playstationet.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.RegisterRequest;
import com.kefessan.playstationet.enumeration.ERole;
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
        
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
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
}