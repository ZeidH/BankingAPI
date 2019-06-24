package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.AccountSpecification;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.AccountRequest;
import nl.Inholland.repository.*;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AccountService extends AbstractService implements VaultSubject {

    private AccountFactory accountFactory;

    private ExecutorService service = Executors.newCachedThreadPool();

    public AccountService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
    }

    @Override
    public void registerVault(VaultObserver vault) {
        this.vault = vault;
    }


    public List<Account> getAccounts(String search) {
        Specification<Account> spec = getBuilder(search).build(searchCriteria -> new AccountSpecification((SpecSearchCriteria) searchCriteria));
        return accoRepo.findAll(spec);
    }

    @Async("AccountTaskExecutor")
    @Transactional
    public void createAccount(AccountRequest request) throws Exception{

        Iban newIban;

        if(request.getBban() == null){
            newIban = IbanGenerator.makeIban(CountryCodeEnum.valueOf(request.getCountryCode()), BankCodeEnum.valueOf(request.getBank()));
        }else{
            newIban = IbanGenerator.makeIban(CountryCodeEnum.valueOf(request.getCountryCode()), BankCodeEnum.valueOf(request.getBank()), request.getBban());
        }

        User activeUser = userRepo.getUserByUsername(request.getUser());


        switch (request.getType().toLowerCase()){
            case "current":
                accountFactory = new CurrentAccountFactory();

                if(activeUser.getIbanList().get(AccountType.Current) == null) {
                    activeUser.addIban(AccountType.Current, newIban);
                }else{
                    throw new Exception();
                }

                break;
            case "savings":
                accountFactory = new SavingsAccountFactory();

                if(activeUser.getIbanList().get(AccountType.Savings) == null) {
                    activeUser.addIban(AccountType.Savings, newIban);
                }else{
                    throw new Exception();
                }

                break;
            default:
                throw new Exception();
        }

        Account newAccount = accountFactory.createAccount(request);

        newAccount.setIban(newIban);

        accoRepo.save(newAccount);

        userRepo.save(activeUser);

        vault.increaseBalance(newAccount.getBalance().getAmount());

    }

    public List<Account> getUserRelatedAccounts(Long id){
        List<Account> accounts = new ArrayList<>();
        User activeUser = userRepo.getOne(id);

        for (Map.Entry<AccountType, Iban> entry : activeUser.getIbanList().entrySet()) {
            accounts.add(accoRepo.getAccountByIban(entry.getValue()));
        }
        return accounts;
    }


    public void deleteAccount(long id) {
        accoRepo.delete(accoRepo.getOne(id));
    }

    public void updateAccountStatus(Long id) {
        Account account = accoRepo.getOne(id);
        if (account.getStatus() == AccountStatusEnum.OPEN){
            account.setStatus(AccountStatusEnum.ClOSED);
        }else{
            account.setStatus(AccountStatusEnum.OPEN);
        }
        accoRepo.save(account);
    }

    public Account getAccount(long id) {
        Account account = accoRepo.getOne(id);
        if (account != null) {
            return account;
        } else {
            throw new NoSuchElementException();
        }
    }
}
