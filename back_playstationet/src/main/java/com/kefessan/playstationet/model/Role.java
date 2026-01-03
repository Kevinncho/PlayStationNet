package com.kefessan.playstationet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole; // Sin AutoIncrement si vas a definir roles fijos (1=USER, 2=ADMIN)

    private String roleName;// ROLE_USER, ROLE_ADMIN
}
