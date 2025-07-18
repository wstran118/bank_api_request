package com.example.bankapi.service;

import com.example.bankapi.dto.AccountDTO;
import com.example.bankapi.entity.Account;
import com.example.bankapi.entity.User;
import com.example.bankapi.repository.AccountRepository;
import com.example.bankapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        User user = userRepository.findById(accountDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account account = new Account();
        account.setUser(user);
        account.setBalance(accountDTO.getBalance() != null ? accountDTO.getBalance() : 0.0);
        accountRepository.save(account);
        accountDTo.setId(account.getId());
        return accountDTO;
    }//end createAccount

    public AccountDTO getAccount(long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUserId(account.getUser().getId());
        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }
}