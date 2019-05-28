package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.SavingsAccount;
import io.swagger.model.Transaction;
import io.swagger.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
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

    public Iterable<Transaction> getAllTransactions(@NotNull @ApiParam(value = "Selects all transactions that belong to user", required = true) @Valid @RequestParam(value = "me", required = true) Boolean me,@ApiParam(value = "The ID of a specific Transaction") @Valid @RequestParam(value = "id", required = false) Integer id,@ApiParam(value = "Date from") @Valid @RequestParam(value = "dateFrom", required = false) String dateFrom,@ApiParam(value = "Date to") @Valid @RequestParam(value = "dateTo", required = false) String dateTo,@ApiParam(value = "Maximum number of entries returned") @Valid @RequestParam(value = "entries", required = false) Integer entries,@ApiParam(value = "Business category", allowableValues = "Living, Entertainment, Food, Transport, Saving, Other") @Valid @RequestParam(value = "category", required = false) String category,@ApiParam(value = "Sorts on amount low to high -> true high to low -> false") @Valid @RequestParam(value = "sort", required = false) Boolean sort,@ApiParam(value = "Filters on selected currency") @Valid @RequestParam(value = "currency", required = false) String currency,@ApiParam(value = "Filters on selected status", allowableValues = "pending, failed, processed") @Valid @RequestParam(value = "status", required = false) String status) {
        String accept = request.getHeader("Accept");
        //String i = request.getParameter("category");
      //  return new ResponseEntity<List>(i, HttpStatus.OK);
        return service.getTransaction();
    }

    public ResponseEntity<Void> updateTransactionStatus(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Body body,@ApiParam(value = "the Transaction id",required=true) @PathVariable("id") Integer id,@ApiParam(value = "callback Url",required=true) @PathVariable("callBackUrl") String callBackUrl) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
