package com.example.bankapi.controller;

import com.example.bankapi.dto.AccountDTO;
import com.example.bankapi.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountConroller {
    
    private final AccountService accountService;

    public AccountConroller(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.createAccount(accountDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER', 'ADMIN')")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }
}