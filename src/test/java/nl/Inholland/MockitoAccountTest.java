package nl.Inholland;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.AccountFactory;
import nl.Inholland.model.Accounts.CurrentAccount;
import nl.Inholland.model.Accounts.CurrentAccountFactory;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoAccountTest {

    @Autowired
    private AccountService service;

    @MockBean // Mocked! Doesn't really exists and won't use real data
    private AccountRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAccountsTest() {

        String search = "";
        Specification<Account> spec = service.getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));

        AccountFactory factory = new CurrentAccountFactory();
        Account acc = factory.createAccount(new AccountRequest("NL", "INHO", "Personal Account", "30", "50"));

        when(repository.findAll(spec)).thenReturn(Stream.of(acc).collect(Collectors.toList()));

        assertEquals(1, service.getAccounts("").size());
    }

    @Test
    public void getAccountTest() {

        String search = "";
        Specification<Account> spec = service.getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));

        AccountFactory factory = new CurrentAccountFactory();
        Account acc = factory.createAccount(new AccountRequest("NL", "INHO", "Personal Account", "30", "50"));

        when(repository.findAll(spec)).thenReturn(Stream.of(acc).collect(Collectors.toList()));

        assertEquals(1, service.getAccounts("").size());
    }

}
