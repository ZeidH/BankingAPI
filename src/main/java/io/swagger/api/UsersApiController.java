package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.InlineResponse200;
import io.swagger.model.User;
import io.swagger.annotations.*;
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
//    public ResponseEntity<List<User>> getUsers(@ApiParam(value = "Acending Alphabetic order is true") @Valid @RequestParam(value = "sorted", required = false, defaultValue = "false") Boolean sorted, @ApiParam(value = "Date from") @Valid @RequestParam(value = "dateFrom", required = false) String dateFrom, @ApiParam(value = "Date to") @Valid @RequestParam(value = "dateTo", required = false) String dateTo, @ApiParam(value = "Maximum number of entries returned") @Valid @RequestParam(value = "entries", required = false, defaultValue = "0") Integer entries) {
//        String accept = request.getHeader("Accept");
//        service.setSorting(sorted);
//        service.setEntries(entries);
//        if(dateFrom != null || dateTo != null){
//            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//            Date from = null;
//            Date to = null;
//            try {
//                from = format.parse(dateFrom);
//                to = format.parse(dateTo);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            service.setDateFrom(from);
//            service.setDateTo(to);
//        }
//        return new ResponseEntity<List<User>>(service.getUsers(),HttpStatus.OK);
//    }

    public ResponseEntity<InlineResponse200> registerUser(@ApiParam(value = "User object"  )  @Valid @RequestBody User user) {
        String accept = request.getHeader("Accept");
        service.registerUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> resetUserPassword(@ApiParam(value = "",required=true) @PathVariable("username") String username,@ApiParam(value = "",required=true) @PathVariable("birthday") String birthday,@ApiParam(value = "",required=true) @PathVariable("IBAN") String IBAN) {
        String accept = request.getHeader("Accept");
       // service.resetPassword(username, birthday, IBAN);
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
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

}
