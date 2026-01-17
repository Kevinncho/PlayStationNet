package com.kefessan.playstationet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kefessan.playstationet.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}