package io.swagger.service;

import io.swagger.QueryBuilder.Specifications.AccountSpecification;
import io.swagger.QueryBuilder.SpecSearchCriteria;
import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.IbanRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService extends AbstractService {


    //new Transaction(new BigDecimal("60.10"),"EUR", "NL02INGB0154356789", CategoryEnum.ENTERTAINMENT, "NL02INGB0154356789", "NL02INGB0153457789", "12-05-2019 22:24:10", StatusEnum.PROCESSED)

    public AccountService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super( userRepo,  tranRepo,  accoRepo,  ibanRepo);
    }

    public List<Account> getAccounts(String search) {
        Specification<Account> spec = getBuilder(search).build(searchCriteria -> new AccountSpecification((SpecSearchCriteria) searchCriteria));
        return accoRepo.findAll(spec);
    }

    //region might not be needed cause of getAccounts
    public Iterable<Account> getSavings() {
        List<Account> accounts = new ArrayList<>();
        for (Account acc : accoRepo.findAll()) {
            if (acc instanceof SavingsAccount) {
                accounts.add(acc);
            }
        }
        return accounts;
    }

    public Iterable<Account> getCurrents() {
        List<Account> accounts = new ArrayList<>();
        for (Account acc : accoRepo.findAll()) {
            if (acc instanceof CurrentAccount) {
                accounts.add(acc);
            }
        }
        return accounts;
    }

    public long registerAccount(Account account) {
        do{
            account.getIban().buildIban();
        }while(ibanRepo.existsByIbanCode(account.getIban().getIbanCode()));

        accoRepo.save(account);
        return account.getId();
    }

    public void deleteAccount(long id) {
        accoRepo.delete(accoRepo.getOne(id));
    }

    public Account getAccount(long id) {
        Account account = accoRepo.getOne(id);
        if (account != null) {
            return account;
        } else {
            throw new NoSuchElementException();
        }
    }

    public Account getAccountByIban(String iban) {
        Account account = accoRepo.getAccountByIban(iban);
        if(account != null){
            return account;
        } else {
            throw new NoSuchElementException();
        }
    }
    //endregion

//    public void registerAccount(Account account) {
//        do {
//            account.getIban().setBban(null);
//            account.getIban().buildIban();
//        } while (ibanRepository.existsByIbanCode(account.getIban().getIbanCode()));
//
//        accountRepository.save(account);
//    }

}
