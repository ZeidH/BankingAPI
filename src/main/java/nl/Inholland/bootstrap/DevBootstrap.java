package nl.Inholland.bootstrap;

import nl.Inholland.enumerations.*;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.Employee;
import nl.Inholland.model.Users.User;
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

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IbanRepository ibanRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private CurrentAccountFactory currentAccountFactory;

    @Autowired
    public DevBootstrap(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {


        User userBart = new Customer("Bart", "fried","potato@hotmail.com", "1234566", "bartS", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");

        User userLisa = new Customer("Lisa", "fried","potato@hotmail.com", "1234566", "lisa", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");





        userRepository.save(userBart);
        userRepository.save(userLisa);


        User employee = new Employee("Bart", "fried","potato@hotmail.com", "1234566", "bart", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019");
        userRepository.save(employee);


    }
}
