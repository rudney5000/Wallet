package com.javacode.walletapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private BigDecimal balance;

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }
}
