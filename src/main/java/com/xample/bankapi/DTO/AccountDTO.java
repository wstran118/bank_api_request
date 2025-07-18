package com.example.bankapi.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private Long userId;
    private Double balance;
}