package io.swagger.api.unitTests;

import io.swagger.api.TransactionsApiController;
import io.swagger.model.Iban;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsApiController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionService service;
    protected List<Transaction> transactions = new ArrayList<>();
    private Transaction transaction;

    @Before
    public void setUp(){
        transaction = new Transaction(new BigDecimal("60.10"),"EUR", new Iban(), Transaction.CategoryEnum.ENTERTAINMENT, new Iban(), new Iban(), "12-05-2019 22:24:10", Transaction.StatusEnum.PROCESSED);
        transactions.add(transaction);
    }

    @Test
    public void givenTransactions_whenGetTransactionsShouldReturnJsonArray() throws Exception{
        List<Transaction> allTransactions = Arrays.asList(transaction);

        given(service.getTransactions("")).willReturn(allTransactions);

//        mvc.perform(get("/guitars"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].brand")
//                        .value(guitar.getBrand()));
    }
}
