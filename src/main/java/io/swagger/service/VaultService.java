package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.BalanceBehaviour;
import io.swagger.model.BalanceIncrease;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class VaultService implements VaultObserver{

    private AccountRepository accountRepository;
    public  BalanceBehaviour balanceBehaviour;
    private Account vault;
    private TransactionObservable transactionObservable;

    public VaultService(AccountRepository accountRepository, TransactionObservable transactionObservable) {
        this.accountRepository = accountRepository;
        this.balanceBehaviour = new BalanceIncrease();
        vault = accountRepository.getOne(new Long(0));
        this.transactionObservable = transactionObservable;

        this.transactionObservable.registerVault(this);
    }

    @Override
    public void updateBalance(BigDecimal amount) {
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accountRepository.save(vault);
    }
}
