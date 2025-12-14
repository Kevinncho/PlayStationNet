package com.kefessan.playstationet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserPaymentMethod")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String paymentType;
    private String provider;
    private String token;
    private String last4Digits;
    private Boolean isDefault;
}
