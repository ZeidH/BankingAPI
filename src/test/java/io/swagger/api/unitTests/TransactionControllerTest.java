package io.swagger.api.unitTests;

import io.swagger.api.TransactionsApiController;
import io.swagger.configuration.APISecurityConfig;
import io.swagger.configuration.ApplicationContextProvider;
import io.swagger.model.Iban;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)@SpringBootTest
//@ContextConfiguration(classes= ApplicationContextProvider.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService tranService;
    protected List<Transaction> transactions = new ArrayList<>();
    private Transaction transaction;

    @InjectMocks
    private TransactionsApiController tranController;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(tranController)
                .build();
        transaction = new Transaction(new BigDecimal("60.10"),"EUR", new Iban(), Transaction.CategoryEnum.ENTERTAINMENT, new Iban(), new Iban(), "12-05-2019 22:24:10", Transaction.StatusEnum.PROCESSED);
        transactions.add(transaction);
    }

    @Test
    public void testHelloWorld() throws Exception {
        mockMvc.perform(get("/Customer/Transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title", Matchers.is("Greetings")))
                .andExpect(jsonPath("$[0].currency").value(transaction.getCurrency()))
              .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testHelloWorldJson() throws Exception {
        mockMvc.perform(get("/hello/json")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.title", Matchers.is("Greetings")))
//                .andExpect(jsonPath("$.value", Matchers.is("Hello World")))
//                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

    @Test
    public void testPost() throws Exception {
        String json = "{\n" +
                "  \"title\": \"Greetings\",\n" +
                "  \"value\": \"Hello World\"\n" +
                "}";
        mockMvc.perform(post("/hello/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("Greetings")))
                .andExpect(jsonPath("$.value", Matchers.is("Hello World")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

//    @MockBean
//    private TransactionService service;
//    protected List<Transaction> transactions = new ArrayList<>();
//    private Transaction transaction;
//
//    @Before
//    public void setUp(){
//        transaction = new Transaction(new BigDecimal("60.10"),"EUR", new Iban(), Transaction.CategoryEnum.ENTERTAINMENT, new Iban(), new Iban(), "12-05-2019 22:24:10", Transaction.StatusEnum.PROCESSED);
//        transactions.add(transaction);
//    }
//
//    @Test
//    public void givenTransactions_whenGetTransactionsShouldReturnJsonArray() throws Exception{
//        List<Transaction> allTransactions = Arrays.asList(transaction);
//
//        given(service.getTransactions("")).willReturn(allTransactions);

//        mvc.perform(get("/guitars"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].brand")
//                        .value(guitar.getBrand()));
//    }
}
