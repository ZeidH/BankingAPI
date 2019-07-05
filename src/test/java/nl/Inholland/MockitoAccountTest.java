package nl.Inholland;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoAccountTest {

    @InjectMocks
    private AccountService service;

    @MockBean // Mocked! Doesn't really exists and won't use real data
    private UserRepository userRepo;
    @MockBean // Mocked! Doesn't really exists and won't use real data
    private TransactionRepository tranRepo;
    @MockBean // Mocked! Doesn't really exists and won't use real data
    private AccountRepository accoRepo;
    @MockBean // Mocked! Doesn't really exists and won't use real data
    private IbanRepository ibanRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new AccountService(userRepo, tranRepo, accoRepo, ibanRepo);
    }


    @Test
    public void getAccountsTest() {

        String search = "";
        Specification<Account> spec = service.getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));

        AccountFactory factory = new CurrentAccountFactory();
        Account acc = factory.createAccount(new AccountRequest("NL", "INHO", "Personal Account", "30", "50", "1.5"));

        when(accoRepo.findAll(spec)).thenReturn(Stream.of(acc).collect(Collectors.toList()));

        assertEquals(1, service.getAccounts("").size());
    }

    @Test
    public void getAccountsByIdTest(){
        AccountFactory factory = new CurrentAccountFactory();
        Account acc = factory.createAccount(new AccountRequest("NL", "INHO", "Personal Account", "30", "50", "1.5"));

        when(accoRepo.getOne(acc.getId())).thenReturn(acc);

        Account testAccount = accoRepo.getOne(acc.getId());

        assertEquals(testAccount.getBalance(), acc.getBalance());
    }

    @Test(expected = Exception.class)
    public void incompleteAccountShouldThrowExeption() throws Exception{
        service.createAccount(new AccountRequest(null, null, null, null, null, null));
    }


}
