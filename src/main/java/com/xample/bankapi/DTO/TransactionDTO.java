package com.example.bankapi.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private Long accountId;
    private Double amount;
    private String type;
}