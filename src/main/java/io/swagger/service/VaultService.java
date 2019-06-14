package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.BalanceBehaviour;
import io.swagger.model.BalanceDecrease;
import io.swagger.model.BalanceIncrease;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VaultService{

    private AccountRepository accountRepository;
    public  BalanceBehaviour balanceBehaviour;
    private Account vault;

    @Autowired
    public VaultService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.balanceBehaviour = new BalanceIncrease();
    }


    public void addBalance(BigDecimal amount) {
        vault = accountRepository.getOne(new Long(0));
        this.balanceBehaviour = new BalanceIncrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accountRepository.save(vault);
    }

    public void substractBalance(BigDecimal amount) {
        vault = accountRepository.getOne(new Long(0));
        this.balanceBehaviour = new BalanceDecrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accountRepository.save(vault);
    }
}
