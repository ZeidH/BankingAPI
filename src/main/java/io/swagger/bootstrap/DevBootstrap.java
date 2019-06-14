package io.swagger.bootstrap;


import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        //---------------------------------VAULT-Maybe make it singleton?------------------------------------------------//
        Account vault = new VaultAccount().name("Bank").balance(new BigDecimal(0.0)).iban(new Iban().bban("0000000001"));
        vault.getIban().buildIban();
        accountRepository.save(vault);
        // T---------------------------------------------------------------------------------------------//

        /////////////////////BART/////////////////////////



        Account accountBart = new CurrentAccount().name("Bart's Current Account").balance(new BigDecimal(100.0)).iban(new Iban());
        accountBart.getIban().buildIban();
        List<Account> accountsBart = new ArrayList<Account>();
        accountsBart.add(accountBart);
        accountRepository.save(accountBart);

        User userBart = new User("Bart", "fried","potato@hotmail.com", "1234566", "bart", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accountsBart);
        userRepository.save(userBart);

        /////////////////////LISA/////////////////////////

        Account accountLisa = new CurrentAccount().name("Lisa's Current Account").balance(new BigDecimal(0.0)).iban(new Iban());
        accountLisa.getIban().buildIban();
        List<Account> accountsLisa = new ArrayList<Account>();
        accountsLisa.add(accountLisa);
        accountRepository.save(accountLisa);

        User userLisa = new User("Lisa", "fried","potato@hotmail.com", "1234566", "lisa", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accountsLisa);
        userRepository.save(userLisa);

        // TEST Bart sends Lisa
        Transaction transaction = new Transaction(new BigDecimal(5), "EUR", accountBart.getIban(), Transaction.CategoryEnum.LIVING, accountBart.getIban(), accountLisa.getIban(), "10-6-2019", Transaction.StatusEnum.PROCESSED);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        transactionRepository.save(transaction);

        //TEST POTATO
        Account account = new CurrentAccount().name("Potato's Current Account").balance(new BigDecimal(0.0)).iban(new Iban());
        account.getIban().buildIban();
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(account);
        accountRepository.save(account);


        User user = new User("potato", "fried","potato@hotmail.com", "1234566", "bill", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accounts);

        userRepository.save(user);

    }
}
