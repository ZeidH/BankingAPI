package io.swagger.api;


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
public class AccountsApiControllerIntegrationTest {

    @Autowired
    private AccountsApi api;

    @Test
    public void deleteAccountTest() throws Exception {
        Integer id = 56;
        ResponseEntity<Void> responseEntity = api.deleteAccount(id);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getAccountTest() throws Exception {
        Integer id = 56;
        ResponseEntity<Object> responseEntity = api.getAccount(id);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        String type = "type_example";
       // ResponseEntity<List<Object>> responseEntity = api.getAllAccounts(type);
        // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void registerAccountTest() throws Exception {
        ResponseEntity<Object> responseEntity = api.registerAccount();
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
