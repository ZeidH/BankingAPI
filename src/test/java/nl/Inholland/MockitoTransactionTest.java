package nl.Inholland;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Transactions.TransactionFactory;
import nl.Inholland.model.Transactions.TransactionFlowFactory;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.model.requests.TransactionRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.TransactionService;
import nl.Inholland.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class MockitoTransactionTest {

    @Autowired // What we're testing here is the service layer.
    private TransactionService service;

    @MockBean // Mocked! Doesn't really exists and won't use real data
    private TransactionRepository repository;

    @Test
    public void getTransactionsTest() {
        String search = "";
        Specification<Transaction> spec = service.getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));

        TransactionRequest request = new TransactionRequest();
        request.setAmount("30");
        TransactionFactory factory = new TransactionFlowFactory(new Customer("Bart", "fried","stefano@gmail.com", "1234566", "bart", "1234", "9-6-2019", "8-6-2019"), IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO), IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO));
        Transaction trans = factory.createTransaction(request);

        when(repository.findAll(spec)).thenReturn(Stream.of(trans).collect(Collectors.toList()));


        assertEquals(1, service.getTransactions("").size());
    }

}
