package nl.Inholland.bootstrap;

import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccount;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Accounts.VaultAccount;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.Employee;
import nl.Inholland.model.Users.User;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
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

        /*

        //---------------------------------VAULT----------------------------------------------------------//
        Account vault = new VaultAccount("Bank", new BigDecimal(0.0), new Iban("0000000001"), AccountStatusEnum.OPEN);
        vault.getIban().buildIban();
        accountRepository.save(vault);
        // ----------------------------------------------------------------------------------------------//

        /////////////////////BART/////////////////////////

        Account accountBart = new CurrentAccount("Bart's Current Account", new BigDecimal(100.0),new Iban(), AccountStatusEnum.OPEN );
        accountBart.getIban().buildIban();
        List<Account> accountsBart = new ArrayList<Account>();
        accountsBart.add(accountBart);
        accountRepository.save(accountBart);

        User userBart = new Customer("Bart", "fried","stefano@gmail.com", "1234566", "bart", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accountsBart);
        userRepository.save(userBart);

        /////////////////////LISA/////////////////////////

        Account accountLisa = new CurrentAccount("Lisa's Current Account", new BigDecimal(0.0),new Iban(), AccountStatusEnum.OPEN );
        accountLisa.getIban().buildIban();
        List<Account> accountsLisa = new ArrayList<Account>();
        accountsLisa.add(accountLisa);
        accountRepository.save(accountLisa);

        User userLisa = new Employee("Lisa", "fried","forThe@gmail.com", "1234566", "lisa", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accountsLisa);
        userRepository.save(userLisa);

        // TEST Bart sends Lisa
        Transaction transaction = new Transaction(new BigDecimal(5), "EUR", accountBart.getIban(), CategoryEnum.LIVING, StatusEnum.PROCESSED,"10-6-2019", accountBart.getIban(), accountLisa.getIban());
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        transactionRepository.save(transaction);

        //TEST Bill
        Account accountBill = new CurrentAccount("Bill's Current Account", new BigDecimal(0.0),new Iban(), AccountStatusEnum.OPEN );
        accountBill.getIban().buildIban();
        List<Account> accountsBill = new ArrayList<Account>();
        accountsBill.add(accountBill);
        accountRepository.save(accountBill);


        User user = new Employee("potato", "fried","heyo1989@hotmail.com", "1234566", "bill", passwordEncoder.encode("1234"), "9-6-2019", "8-6-2019", accountsBill);

        userRepository.save(user);

         */

    }
}
