package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.SavingsAccount;
import io.swagger.model.Transaction;

import java.util.*;

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
        SavingsAccount body = new SavingsAccount();
        ResponseEntity<Void> responseEntity = api.createTransaction(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getAllTransactionsTest() throws Exception {
        Boolean me = true;
        Integer id = 56;
        String dateFrom = "dateFrom_example";
        String dateTo = "dateTo_example";
        Integer entries = 56;
        String category = "category_example";
        Boolean sort = true;
        String currency = "currency_example";
        String status = "status_example";
        ResponseEntity<List<Transaction>> responseEntity = api.getAllTransactions(me, id, dateFrom, dateTo, entries, category, sort, currency, status);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void updateTransactionStatusTest() throws Exception {
        Body body = new Body();
        Integer id = 56;
        String callBackUrl = "callBackUrl_example";
        ResponseEntity<Void> responseEntity = api.updateTransactionStatus(body, id, callBackUrl);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
