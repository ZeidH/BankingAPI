package io.swagger.api.unitTests;

import io.swagger.api.AccountsApiController;
import io.swagger.configuration.APISecurityConfig;
import io.swagger.configuration.ApplicationContextProvider;
import io.swagger.model.Account;
import io.swagger.model.CurrentAccount;
import io.swagger.model.Iban;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes= ApplicationContextProvider.class)
public class AccountControllerTest extends TransactionControllerTest {

    protected List<Account> accounts = new ArrayList<>();

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Account bart = new CurrentAccount().name("Bart's Current Account").balance(new BigDecimal(100.0)).iban(new Iban());
        bart.getIban().buildIban();
        bart.setTransactions(transactions);
        accounts.add(bart);
    }
}
