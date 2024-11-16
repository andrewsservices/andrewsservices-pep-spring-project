package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.naming.AuthenticationException;

import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    public Account register(String userName, String password) throws ResourceNotFoundException{
        Account newAccount = new Account(userName, password);
        accountRepository.save(newAccount);
        return newAccount;
    }



    public Account login(String userName, String password) throws ResourceNotFoundException{
        List<Account> accounts = accountRepository.findAll();
        for(Account a: accounts){
            if(a.getUsername().equals(userName) && (a.getPassword().equals(password))){
                return a;
            }
        }
        throw new ResourceNotFoundException(userName + " was not found");

    }

}