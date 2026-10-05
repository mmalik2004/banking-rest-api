package com.example.bankingapp.service.impl;

import com.example.bankingapp.exception.AccountException;
import com.example.bankingapp.mapper.AccountMapper;
import com.example.bankingapp.model.Account;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.service.AccountService;
import  com.example.bankingapp.dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
         Account account= AccountMapper.mapToAccount(accountDto);
         Account saved=accountRepository.save(account);
         return AccountMapper.mapToAccountDto(saved);

    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(()-> new AccountException("account not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository.findById(id).orElseThrow(()-> new AccountException("account not exist"));
        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account saved=accountRepository.save(account);

        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository.findById(id).orElseThrow(()-> new AccountException("account not exist"));
        if(account.getBalance() <amount){
            throw new RuntimeException("insufficient balance");
        }

        double total=account.getBalance()-amount;
        account.setBalance(total);
        Account saved=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(()-> new AccountException("account not exist"));
        accountRepository.delete(account);
    }
}
