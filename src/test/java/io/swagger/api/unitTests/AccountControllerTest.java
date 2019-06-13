package io.swagger.api.unitTests;

import io.swagger.api.AccountsApiController;
import io.swagger.model.Account;
import io.swagger.model.CurrentAccount;
import io.swagger.model.Iban;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsApiController.class)
public class AccountControllerTest extends TransactionControllerTest {

    protected List<Account> accounts = new ArrayList<>();

    @Override
    @Before
    public void setUp() {
        super.setUp();
        Account bart = new CurrentAccount().name("Bart's Current Account").balance(new BigDecimal(100.0)).iban(new Iban());
        bart.getIban().buildIban();
        bart.setTransactions(transactions);
        accounts.add(bart);
    }
}
