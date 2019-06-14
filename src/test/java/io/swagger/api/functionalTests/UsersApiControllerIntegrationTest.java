package io.swagger.api.functionalTests;

import io.swagger.api.UsersApi;
import io.swagger.model.InlineResponse200;
import io.swagger.model.User;

import java.util.*;

import io.swagger.model.requests.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Response;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersApiControllerIntegrationTest {


    @Autowired
    private UsersApi api;

    private UserRequest userRequest;

    @Before
    public void setUp() throws Exception {
        userRequest = new UserRequest("Bart", "fried","potato@hotmail.com", "1234566", "bart3", "1234", "9-6-2019", "8-6-2019");
    }

    @Test
    public void deleteUserTest() throws Exception {
        Long id = 10000001L;
        ResponseEntity<Void> responseEntity = api.deleteUser(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getUsersTest() throws Exception {
        String search = "";
        ResponseEntity<List<User>> responseEntity = api.getUsers(search);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void registerUserTest() throws Exception {
        ResponseEntity<InlineResponse200> responseEntity = api.registerUser(userRequest);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test(expected = BadCredentialsException.class)
    public void usersLoginPostTest() throws Exception {
        String username = "username_example";
        String password = "password_example";
        ResponseEntity<Map<Object,Object>> responseEntity = api.usersLoginPost(username, password);
    }

}
