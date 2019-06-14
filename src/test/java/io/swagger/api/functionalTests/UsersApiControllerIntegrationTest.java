package io.swagger.api.functionalTests;

import io.swagger.api.UsersApi;
import io.swagger.model.InlineResponse200;
import io.swagger.model.User;

import java.util.*;

import io.swagger.model.requests.UserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Response;

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
    public void getUsersTest() throws Exception {
        String search = "";
        ResponseEntity<List<User>> responseEntity = api.getUsers(search);
       // assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

//    @Test
//    public void registerUserTest() throws Exception {
//        UserRequest body = new UserRequest();
//        ResponseEntity<InlineResponse200> responseEntity = api.registerUser(body);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

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
