package nl.Inholland.controller.Transactions;

import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.requests.TransactionRequest;
import nl.Inholland.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/*
 * Handles endpoints for Transactions
 */

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
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transaction) throws Exception {
        try{
            service.createTransactionFlow(transaction);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Customer/{iban}/Transactions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Transaction>> getAllAuthorizedTransactions(@PathVariable String iban){
        List<Transaction> transactions = service.getTransactionsFromAccount(iban);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Transactions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(value = "search", required = false) String search) {
        return new ResponseEntity<>(service.getTransactions(search),HttpStatus.OK);
    }


}
