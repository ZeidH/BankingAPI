package nl.Inholland.controller.Transactions;

import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.exceptions.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.requests.TransactionRequest;
import nl.Inholland.service.AccountService;
import nl.Inholland.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@Transactional
public class TransactionsApiController {

    private final TransactionService service;

    @Autowired
    public TransactionsApiController(TransactionService service) {
        this.service = service;
    }


    @RequestMapping(value = "/Customer/Transactions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequest transaction) {
        try{
            service.createTransactionFlow(transaction);
        }catch(Exception e) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Customer/{id}/Transactions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Transaction>> getAllAuthorizedTransactions(@PathVariable("id") Long id){
        List<Transaction> transactions = service.getTransactionsFromAccount(id);
        return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
    }

    //Only for Employees
    @RequestMapping(value = "/Employee/Transactions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(value = "search", required = false) String search) {
        return new ResponseEntity<List<Transaction>>(service.getTransactions(search),HttpStatus.OK);
    }


}
