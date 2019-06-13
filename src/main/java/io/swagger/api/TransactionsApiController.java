package io.swagger.api;

import io.swagger.AuthenticatedUser;
import io.swagger.model.Body;
import io.swagger.model.SavingsAccount;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Status;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final TransactionService service;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService service) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
    }

    public ResponseEntity<Void> createTransaction(@ApiParam(value = "Saving accounts whose interest gonna update" ,required=true )  @Valid @RequestBody Transaction transaction) {
        String accept = request.getHeader("Accept");
        service.createTransaction(transaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //Only for Employees
    public ResponseEntity<List<Transaction>> getAllTransactions(@ApiParam(value = "The ID of a specific Transaction") @Valid @RequestParam(value = "search", required = false, defaultValue = "false") String search) {
        return new ResponseEntity<List<Transaction>>(service.getTransactions(search),HttpStatus.OK);

    }
    public ResponseEntity<Void> updateTransactionStatus(Authentication user, @NotNull @ApiParam(value = "newStatus", required = true) @Valid @RequestParam(value = "newStatus", required = false) Transaction.StatusEnum newStatus) {
// CHANGE
        service.updateStatus(((User)user.getPrincipal()).getId(), newStatus);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
