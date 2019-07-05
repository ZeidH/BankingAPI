package nl.Inholland.bootstrap;

import nl.Inholland.enumerations.*;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Transactions.TransactionFlow;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.Employee;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
 * Fills initial in memory database
 */

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private IbanRepository ibanRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        User userBart = new Customer("Bart", "fried","potato@hotmail.com", "1234566", "bartS", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");

        User userLisa = new Customer("Lisa", "fried","potato@hotmail.com", "1234566", "lisa", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");

        userRepository.save(userBart);
        userRepository.save(userLisa);

        Iban ibanBart = IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1111111111");
        Iban ibanLisa = IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "2222222222");

        userBart.addIban(AccountType.Current, ibanBart);
        userLisa.addIban(AccountType.Current, ibanLisa);


        Account  accountBart = new CurrentAccount("Bart's current account", nl.Inholland.enumerations.AccountStatusEnum.OPEN, ibanBart, new Balance(new BigDecimal(30)), new BigDecimal(100));
        Account  accountLisa = new CurrentAccount("Lisa's current account", nl.Inholland.enumerations.AccountStatusEnum.OPEN, ibanLisa, new Balance(new BigDecimal(30)), new BigDecimal(100));

        accountRepository.save(accountBart);
        accountRepository.save(accountLisa);


        userRepository.save(userBart);
        userRepository.save(userLisa);

        Transaction trans_1 = new TransactionFlow(new BigDecimal(5), "EUR", CategoryEnum.OTHER, StatusEnum.PROCESSED, "04-06-19", userBart, ibanBart, ibanLisa);

        transactionRepository.save(trans_1);

        accountBart.addTransaction(trans_1);
        accountLisa.addTransaction(trans_1);

        accountRepository.save(accountBart);
        accountRepository.save(accountLisa);

        User employee = new Employee("Bart", "fried","friedPotato@hotmail.com", "1234566", "bart", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");
        employee.additionalAuthority("ROLE_CUSTOMER");
        userRepository.save(employee);
    }
}
