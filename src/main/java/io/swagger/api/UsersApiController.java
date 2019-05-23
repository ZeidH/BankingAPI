package io.swagger.api;

import io.swagger.model.InlineResponse200;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RequestMapping("/Users")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private UserService service;
    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = new UserService();
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@NotNull @ApiParam(value = "The ID of the Account", required = true) @Valid @RequestParam(value = "id", required = true) Integer id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(@ApiParam(value = "The ID of a specific User") @Valid @RequestParam(value = "id", required = false) Integer id,@ApiParam(value = "Acending Alphabetic order is true") @Valid @RequestParam(value = "sorted", required = false) Boolean sorted,@ApiParam(value = "Date from") @Valid @RequestParam(value = "dateFrom", required = false) String dateFrom,@ApiParam(value = "Date to") @Valid @RequestParam(value = "dateTo", required = false) String dateTo,@ApiParam(value = "Maximum number of entries returned") @Valid @RequestParam(value = "entries", required = false) Integer entries) {
        String accept = request.getHeader("Accept");
        service.setSorting(sorted);
        // Date from, date to, entries
        service.setEntries(entries);
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date from = null;
        Date to = null;
        try {
            from = format.parse(dateFrom);
            to = format.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.setDateFrom(from);
        service.setDateTo(to);
        return service.getUser(id);
    }

    public ResponseEntity<InlineResponse200> registerUser(@ApiParam(value = "User object"  )  @Valid @RequestBody User body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> resetUserPassword(@ApiParam(value = "",required=true) @PathVariable("username") String username,@ApiParam(value = "",required=true) @PathVariable("birthday") String birthday,@ApiParam(value = "",required=true) @PathVariable("IBAN") String IBAN) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> usersLoginPost(@NotNull @ApiParam(value = "The user name for login", required = true) @Valid @RequestParam(value = "username", required = true) String username,@NotNull @ApiParam(value = "The password for login in clear text", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
