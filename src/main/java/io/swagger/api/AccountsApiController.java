package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.Account;
import io.swagger.model.VaultAccount;
import io.swagger.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private AccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountService accountService) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Autowired
    public void setProductService(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseEntity<Void> deleteAccount(@NotNull @ApiParam(value = "The ID of the Account", required = true) @Valid @RequestParam(value = "id", required = true) Integer id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getAccount(@ApiParam(value = "the account id",required=true) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public Iterable<Account> getAllAccounts(@ApiParam(value = "type of accounts to be filter") @Valid @RequestParam(value = "type", required = false) String type) {
        String accept = request.getHeader("Accept");
        //accountService.registerAccount(new VaultAccount());
        Iterable<Account> accountsList = accountService.getAccounts();
        return accountsList;
    }

    public ResponseEntity<Object> registerAccount() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
