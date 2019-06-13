package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private IbanRepository ibanRepository;
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

    public AccountService(AccountRepository accountRepository, IbanRepository ibanRepository) {
        this.accountRepository = accountRepository;
        this.ibanRepository = ibanRepository;
    }

    public Iterable<Account> getAccounts() {

        return accountRepository.findAll();
    }

    public Iterable<Account> getSavings() {
        List<Account> accounts = new ArrayList<>();
        for(Account acc : getAccounts()){
            if(acc instanceof SavingsAccount){
                accounts.add(acc);
            }
        }
        return accounts;
    }

    public Iterable<Account> getCurrents() {
        List<Account> accounts = new ArrayList<>();
        for(Account acc : getAccounts()){
            if(acc instanceof CurrentAccount){
                accounts.add(acc);
            }
        }
        return accounts;
    }

    public void registerAccount(Account account) {
        do{
            account.getIban().setBban(null);
            account.getIban().buildIban();
        }while(ibanRepository.existsByIbanCode(account.getIban().getIbanCode()));

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

    public Account getAccountByIban(String iban) {
        Account account = accountRepository.getAccountByIban(iban);
        if(account != null){
            return account;
        }
        else{
            throw new NoSuchElementException();
        }
    }

}
