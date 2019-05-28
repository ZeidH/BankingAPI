package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.ApiKeyRepository;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private boolean sorted;
    private int entries;
    private Date dateFrom;
    private Date dateTo;

    //new Transaction(new BigDecimal("60.10"),"EUR", "NL02INGB0154356789", CategoryEnum.ENTERTAINMENT, "NL02INGB0154356789", "NL02INGB0153457789", "12-05-2019 22:24:10", StatusEnum.PROCESSED)

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setSorting(boolean sorted){
        this.sorted = sorted;
    }


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account){
        accountRepository.save(account);
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
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

    public void deleteAccount(long id) {
        accountRepository.delete(accountRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

}