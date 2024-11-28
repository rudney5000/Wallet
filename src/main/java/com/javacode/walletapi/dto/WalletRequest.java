package com.javacode.walletapi.dto;

import com.javacode.walletapi.model.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletRequest(
        @NotNull(message = "Wallet ID не может быть null")
        UUID walletId,
        @NotNull(message = "Operation type не может быть null")
        OperationType operationType,
        @Min(value = 0, message = "Amount должно быть больше или равно 0")
        BigDecimal amount
) {
}
