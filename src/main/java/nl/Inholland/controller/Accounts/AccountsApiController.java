package nl.Inholland.controller.Accounts;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.Inholland.exceptions.CurrentAccountAlreadyExistsException;
import nl.Inholland.exceptions.InvalidAccountTypeException;
import nl.Inholland.exceptions.SavingsAccountAlreadyExistsException;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccount;
import nl.Inholland.model.Accounts.SavingsAccount;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.service.AccountService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;



@RestController
public class AccountsApiController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsApiController.class.getName());

    private AccountService accountService;

    @Autowired
    public AccountsApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    ///----------------------------- EMPLOYE -----------------------------------------------///


    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(value = "search", required = false) String search) {
        return new ResponseEntity<>(accountService.getAccounts(search), HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> registerAccount(@RequestBody(required = true) AccountRequest account) throws Exception {
        try{
            accountService.createAccount(account);
        }catch(CurrentAccountAlreadyExistsException e){
            System.out.println(e.getMessage());
           return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
        }catch (SavingsAccountAlreadyExistsException e){
            return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
        }catch (InvalidAccountTypeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Employee/Accounts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Accounts/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> setAccountStatus(@PathVariable("id") Long id) {
        accountService.updateAccountStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Accounts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Accounts", method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<Void> validateRequest(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ///----------------------------- CUSTOMER -----------------------------------------------///

    @RequestMapping(value = "/Customer/{username}/Accounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable("username") String username) {
        return new ResponseEntity<>(accountService.getUserRelatedAccounts(username), HttpStatus.OK);
    }

}
