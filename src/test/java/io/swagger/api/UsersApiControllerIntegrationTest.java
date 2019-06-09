package io.swagger.api;

import io.swagger.model.InlineResponse200;
import io.swagger.model.User;

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
public class UsersApiControllerIntegrationTest {

    @Autowired
    private UsersApi api;

    @Test
    public void deleteUserTest() throws Exception {
        Long id = 56L;
        ResponseEntity<Void> responseEntity = api.deleteUser(id);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUserTest() throws Exception {
        Integer id = 56;
        Boolean sorted = true;
        String dateFrom = "dateFrom_example";
        String dateTo = "dateTo_example";
        Integer entries = 56;
        Iterable<User> responseEntity = api.getUser(id, sorted, dateFrom, dateTo, entries);
       // assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void registerUserTest() throws Exception {
        User body = new User();
        ResponseEntity<InlineResponse200> responseEntity = api.registerUser(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void resetUserPasswordTest() throws Exception {
        String username = "username_example";
        String birthday = "birthday_example";
        String IBAN = "IBAN_example";
        ResponseEntity<Void> responseEntity = api.resetUserPassword(username, birthday, IBAN);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void usersLoginPostTest() throws Exception {
        String username = "username_example";
        String password = "password_example";
        ResponseEntity<Map<Object,Object>> responseEntity = api.usersLoginPost(username, password);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
