package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.SavingsAccount;
import io.swagger.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public void registerAccount(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccount(long id) {
        accountRepository.delete(accountRepository.findOne(id));
    }

    public Account getAccount(long id) {
        Account account = accountRepository.findOne(id);
        if(account != null){
            return account;
        }
        else{
            throw new NoSuchElementException();
        }
    }

}
