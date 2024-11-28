package com.javacode.walletapi.service;

import com.javacode.walletapi.dto.WalletRequest;
import com.javacode.walletapi.entity.Wallet;

import java.util.List;

public interface WalletService {

    void createWallet();
    List<Wallet> getWallets();
    Wallet getBalance(String WalletId);
    void updateWallet(WalletRequest walletRequest);
}
