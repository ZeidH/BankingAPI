package nl.Inholland.model;

import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserTest {

    public static final String EXPECTED_NAME = "Bart";
    public static final String EXPECTED_LAST_NAME = "fried";
    public static final String EXPECTED_EMAIL = "stefano@gmail.com";
    public static final String EXPECTED_PHONE= "1234566";
    public static final String EXPECTED_USERNAME = "bart";
    public static final String EXPECTED_PASS = "1234";
    public static final String EXPECTED_DATE = "9-6-2019";
    public static final String EXPECTED_BIRTHDAY = "8-6-2019";

    private User user;

    @Before
    public void setUp(){
       user = new Customer("Bart", "fried","stefano@gmail.com", "1234566", "bart", "1234", "9-6-2019", "8-6-2019");
    }

    @Test
    public void createAccountsShouldNotBeNull(){
        assertNotNull(user);
    }

    @Test
    public void testUserDetails(){
        Assert.assertEquals(EXPECTED_NAME, user.getFirstName());
        Assert.assertEquals(EXPECTED_LAST_NAME, user.getLastName());
        Assert.assertEquals(EXPECTED_EMAIL, user.getEmail());
        Assert.assertEquals(EXPECTED_PHONE, user.getPhone());
        Assert.assertEquals(EXPECTED_USERNAME, user.getUsername());
        Assert.assertEquals(EXPECTED_PASS, user.getPassword());
        Assert.assertEquals(EXPECTED_DATE, user.getDateCreated());
        Assert.assertEquals(EXPECTED_BIRTHDAY, user.getBirthday());

    }

    @Test
    public void customerIsIntanceOfUser() {
        assertTrue(user instanceof User);
    }


}
