package com.javacode.walletapi.controller;

import com.javacode.walletapi.dto.WalletRequest;
import com.javacode.walletapi.entity.Wallet;
import com.javacode.walletapi.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/wallet/new")
    public ResponseEntity<String> createWallet() {
        walletService.createWallet();
        return ResponseEntity.ok("Новый кошелек успешно создан!");
    }

    @PostMapping("wallet")
    public ResponseEntity<String> executeOperation(@Valid @RequestBody WalletRequest request) throws RuntimeException{
        walletService.updateWallet(request);

        return ResponseEntity.ok("Выполнение операции успешно завершено!");
    }

    @GetMapping("wallets")
    public ResponseEntity<List<Wallet>> getWallets() {
        return ResponseEntity.ok(walletService.getWallets());
    }

    @GetMapping("/wallets/{WALLET_ID}")
    public ResponseEntity<Wallet> getWalletBalance(@PathVariable("WALLET_ID") String WALLET_ID) {
        return ResponseEntity.ok(walletService.getBalance(WALLET_ID));
    }
}
