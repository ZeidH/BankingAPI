package nl.Inholland.model;

import nl.Inholland.enumerations.*;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Transactions.TransactionFlow;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class TransactionTest {


    public static final BigDecimal EXPECTED_AMOUNT = new BigDecimal(10);
    public static final String EXPECTED_CURRENCY = "EUR";
    public static final CategoryEnum EXPECTED_CATEGORY = CategoryEnum.OTHER;
    public static final StatusEnum EXPECTED_STATUS = StatusEnum.PROCESSED;

    private TransactionFlow transactionFlow;

    @Before
    public void setUp(){
        transactionFlow = new TransactionFlow(new BigDecimal(10), "EUR", new Customer(), CategoryEnum.OTHER, StatusEnum.PROCESSED, "10-10-2010", new Iban(), new Iban() );
    }

    @Test
    public void createTransactionsShouldNotBeNull(){
        Transaction test = new TransactionFlow();
        assertNotNull(test);
    }

    @Test
    public void testTransactionDetails(){
        Assert.assertEquals(EXPECTED_AMOUNT, transactionFlow.getAmount());
        Assert.assertEquals(EXPECTED_CURRENCY, transactionFlow.getCurrency());
        Assert.assertEquals(EXPECTED_CATEGORY, transactionFlow.getCategory());
        Assert.assertEquals(EXPECTED_STATUS, transactionFlow.getStatus());
    }
}
