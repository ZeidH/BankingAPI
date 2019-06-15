package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.Account;
import io.swagger.model.InlineResponse200;
import io.swagger.model.User;
import io.swagger.annotations.*;
import io.swagger.model.requests.UserRequest;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.activation.registries.LogSupport.log;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final UserService service;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService service) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
    }

    public ResponseEntity<Void> deleteUser(@NotNull @ApiParam(value = "The ID of the Account", required = true) @Valid @RequestParam(value = "id", required = true) Long id) {
        String accept = request.getHeader("Accept");
        service.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(@ApiParam(value = "The ID of a specific User") @Valid @RequestParam(value = "id", required = true) Long id){
        return new ResponseEntity<User>(service.getUser(id), HttpStatus.OK);
    }


    public ResponseEntity<List<User>> getUsers(@ApiParam(value = "Acending Alphabetic order is true") @Valid @RequestParam(value = "search", required = false, defaultValue = "false") String search){
        return new ResponseEntity<List<User>>(service.getUsers(search),HttpStatus.OK);
    }

    public ResponseEntity<InlineResponse200> registerUser(@ApiParam(value = "type of accounts to be created") @Valid @RequestBody(required = true) UserRequest userRequest) {
        User newUser = new User();
        if(userRequest != null){
            if(!userRequest.getFirstName().equals(null) || !userRequest.getFirstName().isEmpty()) newUser.firstName(userRequest.getFirstName());
            if(!userRequest.getLastName().equals(null) || !userRequest.getLastName().isEmpty()) newUser.lastName(userRequest.getLastName());
            if(!userRequest.getEmail().equals(null) || !userRequest.getEmail().isEmpty()) newUser.email(userRequest.getEmail());
            if(!userRequest.getPhone().equals(null) || !userRequest.getPhone().isEmpty()) newUser.phone(userRequest.getPhone());
            if(!userRequest.getUsername().equals(null) || !userRequest.getUsername().isEmpty()) newUser.username(userRequest.getUsername());
            if(!userRequest.getPassword().equals(null) || !userRequest.getPassword().isEmpty()) newUser.password(userRequest.getPassword());
            if(!userRequest.getDateCreated().equals(null) || !userRequest.getDateCreated().isEmpty()) newUser.dateCreated(userRequest.getDateCreated());
            if(!userRequest.getBirthday().equals(null) || !userRequest.getBirthday().isEmpty()) newUser.birthday(userRequest.getBirthday());
        }else{
            //newUser.name("Placeholder").balance(new BigDecimal(0));
        }
        service.registerUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Map<Object,Object>> usersLoginPost(@NotNull @ApiParam(value = "The user name for login", required = true) @Valid @RequestParam(value = "username", required = true) String username,@NotNull @ApiParam(value = "The password for login in clear text", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        try {
            String token = service.auth(username, password);
            Map<Object, Object> key = new HashMap<>();
            key.put("username", username);
            key.put("token", token);
            return new ResponseEntity<Map<Object,Object>>(key, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }

    }

    public ResponseEntity<Void> attachAccountToUser(@NotNull @ApiParam(value = "user id", required = true) @Valid @RequestParam(value = "userId", required = true) long userId, @NotNull @ApiParam(value = "account id", required = true) @Valid @RequestParam(value = "accountId", required = true) long accountId){
        service.attachAccount(userId, accountId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> validateRequest(){
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
