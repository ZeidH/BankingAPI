package nl.Inholland.model;

import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.math.BigDecimal;

public class AccountTest {

    public static final String EXPECTED_NAME = "Test";
    public static final AccountStatusEnum EXPECTED_STATUS = AccountStatusEnum.OPEN;
    public static final Iban EXPECTED_IBAN = IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890");
    public static final Balance EXPECTED_BALANCE = new Balance(new BigDecimal(100));
    public static final BigDecimal EXPECTED_DAILY_LIMIT = new BigDecimal(200);
    public static final BigDecimal EXPECTED_INTEREST_RATE = new BigDecimal(1.5);
    private CurrentAccount current;
    private SavingsAccount savings;

    @Before
    public void setUp(){
        current = new CurrentAccount("Test", AccountStatusEnum.OPEN, IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890"), new Balance(new BigDecimal(100)), new BigDecimal(200));
        savings = new SavingsAccount("Test", AccountStatusEnum.OPEN, IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890"), new Balance(new BigDecimal(100)), new BigDecimal(1.5));
    }

    @Test
    public void createAccountsShouldNotBeNull(){
        Account test = new CurrentAccount();
        assertNotNull(test);
        test = new SavingsAccount();
        assertNotNull(test);
    }

    @Test
    public void testAccountDetails(){
        Assert.assertEquals(EXPECTED_NAME, current.getName());
        Assert.assertEquals(EXPECTED_NAME, savings.getName());

        Assert.assertEquals(EXPECTED_STATUS, current.getStatus());
        Assert.assertEquals(EXPECTED_STATUS, savings.getStatus());

        Assert.assertEquals(EXPECTED_IBAN, current.getIban());
        Assert.assertEquals(EXPECTED_IBAN, savings.getIban());

        Assert.assertEquals(EXPECTED_BALANCE, current.getBalance());
        Assert.assertEquals(EXPECTED_BALANCE, savings.getBalance());

        Assert.assertEquals(EXPECTED_DAILY_LIMIT, current.getDailyLimit());
        Assert.assertEquals(EXPECTED_INTEREST_RATE, savings.getInterestRate());
    }
}
