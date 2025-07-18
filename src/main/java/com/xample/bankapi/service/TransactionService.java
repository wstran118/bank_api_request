package com.example.bankapi.service;

import com.example.bankapi.dto.TransactionDTO;
import com.example.bankapi.entity.Account;
import com.example.bankapi.entity.Transaction;
import com.example.bankapi.repository.AccountRepository;
import com.example.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionDTO processTransaction(TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(transactionDTO.getAccountId())
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        
        if (transactionDTO.getType().equals("DEPOSIT")) {
            account.setBalance(account.getBalance() + transactionDTO.getAmount());
        } else if (transactionDTO.getType().equals("WITHDRAWAL")) {
            if (account.getBalance() < transactionDTO.getAmount()) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            account.setBalance(account.getBalance() - transactionDTO.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transactionRepository.save(transaction);
        
        transactionDTO.setId(transaction.getId());
        return transactionDTO;
    }

    public TransactionDTO getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAccountId(transaction.getAccount().getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setType(transaction.getType());
        return transactionDTO;
    }
}