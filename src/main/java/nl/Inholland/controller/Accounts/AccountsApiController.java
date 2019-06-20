package nl.Inholland.controller.Accounts;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccount;
import nl.Inholland.model.Accounts.SavingsAccount;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountsApiController {

    private AccountService accountService;
    private IbanRepository ibanRepository;

    @Autowired
    public AccountsApiController(AccountService accountService, IbanRepository ibanRepository) {
        this.accountService = accountService;
        this.ibanRepository = ibanRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/Employee/Accounts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/Customer/Accounts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> getAccount(@PathVariable("id") Integer id) {
        return new ResponseEntity<Account>(accountService.getAccount(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(value = "search", required = false) String search) {
        return new ResponseEntity<List<Account>>(accountService.getAccounts(search), HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> registerAccount(@RequestBody(required = true) AccountRequest account) {
        Account newAccount = new CurrentAccount();

        if(account == null){
            newAccount.setName("Placeholder");
            newAccount.setBalance(new BigDecimal(0));
        }else{
            if(!account.getName().equals(null) || !account.getName().isEmpty()) newAccount.setName(account.getName());
            if(!account.getBalance().equals(null) || !account.getBalance().isEmpty()) newAccount.setBalance(new BigDecimal(account.getBalance()));
        }

        accountService.registerAccount(newAccount);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Employee/Accounts/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> setAccountStatus(@PathVariable("id") Long id) {
        accountService.updateAccountStatus(id);
        return null;
    }

    @RequestMapping(value = "/Employee/Accounts/Savings", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Account> createSavingsAccount(@RequestParam() String iban) {


        // MOVE TO SERVICE > REPOSITORIES NOT ALLOWED IN CONTROLLER
        if(ibanRepository.existsByIbanCode(iban)){
            Account owner = accountService.getAccountByIban(iban);
            Account savings = new SavingsAccount(owner);
            accountService.registerAccount(savings);
            return new ResponseEntity<Account>(HttpStatus.OK);
        }
        return new ResponseEntity<Account>(HttpStatus.OK);

    }

    @RequestMapping(value = "/Customer/Accounts/Withdraw", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> withDrawal(@RequestParam String iban, @RequestParam(defaultValue = "0") String amount) {
        accountService.withdrawal(iban, new BigDecimal(amount));
        return null;
    }

    @RequestMapping(value = "/Customer/Accounts/Insert", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> insertBalance(@RequestParam String iban, @RequestParam(defaultValue = "0") String amount) {
        accountService.insertBalance(iban, new BigDecimal(amount));
        return null;
    }

    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<Void> validateRequest(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}