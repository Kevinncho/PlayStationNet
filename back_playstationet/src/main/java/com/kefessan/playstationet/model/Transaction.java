package com.kefessan.playstationet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;

    // Relación Opcional: Una transacción puede ser de una orden (compra juegos)...
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    // ... O puede ser de una suscripción
    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = true)
    private UserSubscription subscription;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private UserPaymentMethod paymentMethod;

    private String referenceId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime date;
    private String description;
}