package com.example.service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //Checks if an account exists
    public boolean checkAccountExists(Account account){
        return accountRepository.findAccountByUsername(account.getUsername())!=null;
    }


    public Account addAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        if(password.length()>3 && username.length()>0){
            return accountRepository.save(account);
        }
        return null;
    }


    public Account login(Account account){
        Account actualAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (actualAccount!= null && actualAccount.getPassword().equals(account.getPassword())) return actualAccount;
        return null;
    }


}
