package com.javacode.walletapi.service.impl;

import com.javacode.walletapi.dto.WalletRequest;
import com.javacode.walletapi.entity.Wallet;
import com.javacode.walletapi.exception.InsufficientFundsException;
import com.javacode.walletapi.exception.WalletNotChangedException;
import com.javacode.walletapi.exception.WalletNotFoundException;
import com.javacode.walletapi.model.OperationType;
import com.javacode.walletapi.repository.WalletRepository;
import com.javacode.walletapi.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void createWallet() {
        walletRepository.save(new Wallet(BigDecimal.ZERO));
    }

    @Override
    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet getBalance(String WalletId) {
        return walletRepository.findById(UUID.fromString(WalletId))
                .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));
    }

    @Transactional
    @Override
    public void updateWallet(WalletRequest walletRequest) {
        Wallet wallet = walletRepository.findByIdWithLock(walletRequest.walletId())
                .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));

        if(walletRequest.operationType() == OperationType.DEPOSIT) {
            deposit(wallet, walletRequest.amount());
        } else if (walletRequest.operationType() == OperationType.WITHDRAW) {
            withdraw(wallet, walletRequest.amount());
        } else {
            throw new WalletNotChangedException("Неверный тип операции");
        }
    }

    private void deposit(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));
    }

    private void withdraw(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на кошельке");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
    }
}
