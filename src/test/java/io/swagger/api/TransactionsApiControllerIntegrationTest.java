package io.swagger.api;

import io.swagger.AuthenticatedUser;
import io.swagger.model.Body;
import io.swagger.model.SavingsAccount;
import io.swagger.model.Transaction;

import java.util.*;

import io.swagger.model.User;
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
        Transaction body = new Transaction();
        ResponseEntity<Void> responseEntity = api.createTransaction(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
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
