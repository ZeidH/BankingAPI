package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.SavingsAccount;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.requests.TransactionRequest;
import io.swagger.service.AccountService;
import io.swagger.security.IAuthenticationFacade;
import io.swagger.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.annotation.*;


import javax.transaction.Status;
import javax.transaction.Transactional;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RestController
@Transactional
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final TransactionService service;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    private final AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService service, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
        this.accountService = accountService;
    }


    public ResponseEntity<Void> createTransaction(@ApiParam(value = "Saving accounts whose interest gonna update" ,required=true )  @Valid @RequestBody TransactionRequest transaction) {
        String accept = request.getHeader("Accept");


        if(transaction.getSender() == transaction.getReceiver()) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        Instant instant = Instant.now();

        Transaction newTransaction = new Transaction();

        try{
            newTransaction.setCreator(accountService.getAccountByIban(transaction.getCreator()).getIban());
            newTransaction.setSender(accountService.getAccountByIban(transaction.getSender()).getIban());
            newTransaction.setReceiver(accountService.getAccountByIban(transaction.getReceiver()).getIban());
        }catch(Exception e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        newTransaction.setAmount(new BigDecimal(transaction.getAmount()));
        newTransaction.setDateCreated(instant.toString());
        newTransaction.setCategory(Transaction.CategoryEnum.fromValue(transaction.getCategory()));
        newTransaction.category(Transaction.CategoryEnum.OTHER).currency("EUR").status(Transaction.StatusEnum.PENDING);

        if(!service.notSendingFromSavingsToThirdParty(newTransaction.getSender(), newTransaction.getReceiver())) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        if(!accountService.bothAccountsActive(newTransaction)) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        if(!accountService.sufficientFunds(newTransaction)) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);


        service.createTransaction(newTransaction);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<Transaction>> getAllAuthorizedTransactions(@ApiParam(value = "account Id") @Valid @RequestParam(value = "accountId", required = true) Long id){
        List<Transaction> transactions = service.getTransactionsFromAccount(id);
        return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
    }

    //Only for Employees
    public ResponseEntity<List<Transaction>> getAllTransactions(@ApiParam(value = "Search Parameters") @Valid @RequestParam(value = "search", required = false, defaultValue = "false") String search) {
        return new ResponseEntity<List<Transaction>>(service.getTransactions(search),HttpStatus.OK);
    }


}
