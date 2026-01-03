package com.kefessan.playstationet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kefessan.playstationet.enumeration.ERole;
import com.kefessan.playstationet.model.Role;
import com.kefessan.playstationet.repository.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            for (ERole roleEnum : ERole.values()) {

                String roleName = roleEnum.name();

                roleRepository.findByRoleName(roleName)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName(roleName);
                        roleRepository.save(role);

                        System.out.println("✅ Rol creado: " + roleName);
                        return role;
                    });
            }
        };
    }
}