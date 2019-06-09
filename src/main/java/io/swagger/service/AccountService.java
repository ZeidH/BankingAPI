package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Iban;
import io.swagger.model.SavingsAccount;
import io.swagger.model.VaultAccount;
import io.swagger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAccounts() {

        return accountRepository.findAll();
    }

    public void registerAccount(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccount(long id) {
        accountRepository.delete(accountRepository.findOne(id));
    }

    public Account getAccount(long id) {
        Account account = accountRepository.findOne(id);
        if(account != null){
            return account;
        }
        else{
            throw new NoSuchElementException();
        }
    }

    /*
    public boolean validateIBAN(Iban ibanNumber){ //taken from wikipedia: https://en.wikipedia.org/wiki/International_Bank_Account_Number#Validating_the_IBAN

        String bankCode = codeToBase36(ibanNumber.BANK);
        String countryCode = codeToBase36(ibanNumber.getCountryCode().toString());
        String arrangedCodes = bankCode + ibanNumber.getBban() + countryCode + ibanNumber.getCheckDigits();

        Integer arrengedNumber = Integer.parseInt(arrangedCodes);

        if(arrengedNumber % 97 == 1){
            return true;
        }else{
            return false;
        }

    }

    public String codeToBase36(String bankCode){

        String base36code = "";

        for (char ch: bankCode.toCharArray()) {
            BigInteger big = new BigInteger(String.valueOf(ch), 16);
            base36code += big.toString(36);
        }

        return base36code;
    }
    */

}
