package com.javacode.walletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.walletapi.dto.WalletRequest;
import com.javacode.walletapi.entity.Wallet;
import com.javacode.walletapi.model.OperationType;
import com.javacode.walletapi.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.ZERO);
    }

    @Test
    public void testCreateWallet() throws Exception {
        mockMvc.perform(post("/api/v1/wallet/new"))
                .andExpect(status().isOk())
                .andExpect(content().string("Новый кошелек успешно создан!"));
    }

    @Test
    public void testGetWalletBalance() throws Exception {
        when(walletService.getBalance(anyString())).thenReturn(wallet);

        mockMvc.perform(get("/api/v1/wallets/{WALLET_ID}", wallet.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(wallet.getId().toString()))
                .andExpect(jsonPath("$.balance").value(wallet.getBalance().intValue()));
    }

    @Test
    public void testGetWallets() throws Exception {
        List<Wallet> wallets = Arrays.asList(wallet);
        when(walletService.getWallets()).thenReturn(wallets);

        mockMvc.perform(get("/api/v1/wallets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(wallets.size()))
                .andExpect(jsonPath("$[0].id").value(wallet.getId().toString()))
                .andExpect(jsonPath("$[0].balance").value(wallet.getBalance().intValue()));
    }

    @Test
    public void testExecuteOperation() throws Exception {
        WalletRequest request = new WalletRequest(
                wallet.getId(),
                OperationType.DEPOSIT,
                BigDecimal.TEN
        );
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Выполнение операции успешно завершено!"));
    }
}