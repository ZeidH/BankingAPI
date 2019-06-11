package io.swagger.bootstrap;


import io.swagger.model.Account;
import io.swagger.model.Iban;
import io.swagger.model.VaultAccount;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public DevBootstrap(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        Account vault = new VaultAccount().name("Bank").balance(new BigDecimal(0.0)).iban(new Iban().bban("0000000001"));
        vault.getIban().buildIban();
        accountRepository.save(vault);


    }
}
