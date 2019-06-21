package nl.Inholland.controller.Transactions;

import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
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
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transaction) {
        /*
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
        newTransaction.setCategory(CategoryEnum.fromValue(transaction.getCategory()));
        newTransaction.setCategory(CategoryEnum.OTHER);
        newTransaction.setCurrency("EUR");
        newTransaction.setStatus(StatusEnum.PENDING);

        if(!service.notSendingFromSavingsToThirdParty(newTransaction.getSender(), newTransaction.getReceiver())) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        if(!accountService.bothAccountsActive(newTransaction)) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        if(!accountService.sufficientFunds(newTransaction)) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);


        service.createTransaction(newTransaction);

         */
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Customer/Transactions/{id}", method = RequestMethod.GET)
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
