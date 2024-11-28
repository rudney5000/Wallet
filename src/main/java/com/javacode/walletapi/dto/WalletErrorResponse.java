package com.javacode.walletapi.dto;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@Getter
@Setter
@XmlRootElement
public class WalletErrorResponse {

    private String errorCode;
    private LocalDateTime timestamp;
    private String description;
    private String tip;

    public WalletErrorResponse() {
    }

    public WalletErrorResponse(String errorCode, String description, String tip) {
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.tip = tip;
    }
}
