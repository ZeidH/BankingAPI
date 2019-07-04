package nl.Inholland.controller;

import nl.Inholland.enumerations.*;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Transactions.TransactionFlow;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TransactionsControllerTest {

    @MockBean
    private TransactionService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        transaction = new TransactionFlow(new BigDecimal(10), "EUR", new Customer(), CategoryEnum.OTHER, StatusEnum.PROCESSED, "10-10-2010", IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890"), IbanGenerator.makeIban(CountryCodeEnum.NL, BankCodeEnum.INHO, "1234567890") );
    }


    @Test
    public void transactionsShouldReturnJsonArray() throws Exception {
        mvc.perform(get("/Employee/Transactions"))
                .andExpect(status().isOk());
      }

    @Test
    public void createTransactionShouldReturnCreated() throws Exception {
        mvc.perform(post("/Customer/Transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());

    }



}
