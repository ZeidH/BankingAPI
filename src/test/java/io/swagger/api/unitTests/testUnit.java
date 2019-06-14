package io.swagger.api.unitTests;


import io.swagger.api.UsersApiController;
import io.swagger.model.Iban;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(UsersApiController.class)
public class testUnit {

    @MockBean
    private TransactionService service;
    private Transaction transaction;

    @Before
    public void setUp(){
        transaction = new Transaction(new BigDecimal("60.10"),"EUR", new Iban(), Transaction.CategoryEnum.ENTERTAINMENT, new Iban(), new Iban(), "12-05-2019 22:24:10", Transaction.StatusEnum.PROCESSED);
    }
    @Test
    public void trans(){
        service.createTransaction(transaction);
    }
}
