package io.swagger.api.functionalTests;

import io.swagger.api.TransactionsApi;
import io.swagger.model.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsApiControllerIntegrationTest {

    @Autowired
    private TransactionsApi api;

    @Test
    public void createTransactionTest() throws Exception {
//        Transaction body = new Transaction(new BigDecimal("60.10"),"EUR", new Iban(), Transaction.CategoryEnum.ENTERTAINMENT, new Iban(), new Iban(), "12-05-2019 22:24:10", Transaction.StatusEnum.PROCESSED);
//        ResponseEntity<Void> responseEntity = api.createTransaction(body);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getAllTransactionsTest() throws Exception {
        String search = "";

        ResponseEntity<List<Transaction>> responseEntity = api.getAllTransactions(search);
        //assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

//    @Test
//    @Ignore
//    public void updateTransactionStatusTest() throws Exception {
//        User user = new User();
//        Transaction.StatusEnum newStatus = Transaction.StatusEnum.PROCESSED;
//        ResponseEntity<Void> responseEntity = api.updateTransactionStatus(user, newStatus);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

}
