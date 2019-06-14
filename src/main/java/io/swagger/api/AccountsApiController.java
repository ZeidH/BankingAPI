package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.*;
import io.swagger.model.requests.AccountRequest;
import io.swagger.repository.IbanRepository;
import io.swagger.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private AccountService accountService;
    private IbanRepository ibanRepository;

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountService accountService, IbanRepository ibanRepository) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.request = request;
        this.ibanRepository = ibanRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseEntity<Void> deleteAccount(@NotNull @ApiParam(value = "The ID of the Account", required = true) @Valid @RequestParam(value = "id", required = true) Integer id) {
        String accept = request.getHeader("Accept");
        accountService.deleteAccount(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccount(@ApiParam(value = "the account id",required=true) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");

        return new ResponseEntity<Account>(accountService.getAccount(id), HttpStatus.OK);
    }

    /*
    public ResponseEntity<Account> getAccountByIban(@ApiParam(value = "the account id",required=true) @PathVariable("iban") String iban) {
        String accept = request.getHeader("Accept");

        return new ResponseEntity<Account>(accountService.getAccountByIban(iban), HttpStatus.OK);
    }*/

    public ResponseEntity<List<Account>> getAllAccounts(@ApiParam(value = "search criteria") @Valid @RequestParam(value = "type", required = false, defaultValue = "") String search) {
        return new ResponseEntity<List<Account>>(accountService.getAccounts(search),HttpStatus.OK);


        //        String accept = request.getHeader("Accept");
//
//        type = type.toLowerCase();
//
//        Iterable<Account> accountsList;
//
//        if(type.equals("currents")){
//            accountsList = accountService.getCurrents();
//        }else if(type.equals("savings")){
//            accountsList = accountService.getSavings();
//        }else{
//            accountsList = accountService.getAccounts();
//        }
//        return accountsList;
    }


    public ResponseEntity<Object> registerAccount(@ApiParam(value = "type of accounts to be created") @Valid @RequestBody(required = false) AccountRequest account) {
        String accept = request.getHeader("Accept");

        Account newAccount = new CurrentAccount();

        if(account == null){
            newAccount.name("Placeholder").balance(new BigDecimal(0));
        }else{
            if(!account.getName().equals(null) || !account.getName().isEmpty()) newAccount.name(account.getName());
            if(!account.getBalance().equals(null) || !account.getBalance().isEmpty()) newAccount.balance(new BigDecimal(account.getBalance()));
        }
        
        accountService.registerAccount(newAccount);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> setAccountStatus(@NotNull @Valid Long id) {
        accountService.updateAccountStatus(id);
        return null;
    }

    @Override
    public ResponseEntity<Account> createSavingsAccount(@Valid String iban) {
        if(ibanRepository.existsByIbanCode(iban)){
            Account owner = accountService.getAccountByIban(iban);
            Account savings = new SavingsAccount(owner);
            accountService.registerAccount(savings);
            return new ResponseEntity<Account>(HttpStatus.OK);
        }
        return new ResponseEntity<Account>(HttpStatus.OK);

    }
}
