package io.swagger.bootstrap;


import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

//        Account vault = new VaultAccount().id(0).name("Bank").balance(new BigDecimal(0.0)).iban(new Iban(0, Iban.CountryCodeEnum.NL, "0000000001"));
//
//        accountRepository.save(vault);

        // TEST

        Account vault = new VaultAccount().name("Bank").balance(new BigDecimal(0.0)).iban(new Iban().bban("0000000001"));
        vault.getIban().buildIban();
        accountRepository.save(vault);

        // TEST
        Transaction transaction = new Transaction(new BigDecimal(5), "EUR", new Iban().bban("0000000001"), Transaction.CategoryEnum.LIVING, new Iban().bban("0000000002"), new Iban().bban("0000000002"), "10-6-2019", Transaction.StatusEnum.PROCESSED);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        transactionRepository.save(transaction);

        Account account = new CurrentAccount().name("Potato's Current Account").balance(new BigDecimal(0.0)).iban(new Iban());
        account.getIban().buildIban();
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(account);
        accountRepository.save(account);


        User user = new User("potato", "fried","potato@hotmail.com", "1234566", "bill", "1234", "9-6-2019", "8-6-2019", accounts);
        userRepository.save(user);

    }
}
