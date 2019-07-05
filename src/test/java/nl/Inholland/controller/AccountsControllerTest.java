package nl.Inholland.controller;

import nl.Inholland.controller.Accounts.AccountsApiController;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AccountsControllerTest {

    @Mock
    AccountService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    private Account current;
    private Account savings;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        current = new CurrentAccount("Test", AccountStatusEnum.OPEN, IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890"), new Balance(new BigDecimal(100)), new BigDecimal(200));
        savings = new SavingsAccount("Test", AccountStatusEnum.OPEN, IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890"), new Balance(new BigDecimal(100)), new BigDecimal(1.5));

    }

    @Test
    public void getVaultAsJsonArray() throws Exception {

        mvc.perform(get("/Employee/Accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bank"));
    }

    @Test
    public void givenAccountsShouldReturnJsonArray() throws Exception {
        List<Account> accounts = Arrays.asList(current, savings);
        given(service.getAccounts("")).willReturn(accounts);

        mvc.perform(get("/Employee/Accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
      }

    @Test
    public void createAccountShouldReturnCreated() throws Exception {
        mvc.perform(post("/Employee/Accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void deleteReturnsOk() throws Exception {
        mvc.perform(delete("/Employee/Accounts/" + 1))
                .andExpect(status().isOk());

    }



}
