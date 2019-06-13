package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.BalanceBehaviour;
import io.swagger.model.BalanceDecrease;
import io.swagger.model.BalanceIncrease;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class VaultService implements VaultObserver{

    private AccountRepository accountRepository;
    public  BalanceBehaviour balanceBehaviour;
    private Account vault;


    public VaultService(AccountRepository accountRepository, TransactionObservable transactionObservable) {
        this.accountRepository = accountRepository;
        this.balanceBehaviour = new BalanceIncrease();
        vault = accountRepository.getOne(new Long(0));
    }


    @Override
    public void addBalance(BigDecimal amount) {
        this.balanceBehaviour = new BalanceIncrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accountRepository.save(vault);
    }

    @Override
    public void substractBalance(BigDecimal amount) {
        this.balanceBehaviour = new BalanceDecrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accountRepository.save(vault);
    }
}
