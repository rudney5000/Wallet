package com.javacode.walletapi.exception;

public class WalletNotChangedException extends RuntimeException {
    public WalletNotChangedException(String message) {
        super(message);
    }
}
