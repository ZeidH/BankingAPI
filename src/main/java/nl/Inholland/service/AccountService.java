package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.AccountSpecification;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.exceptions.CurrentAccountAlreadyExistsException;
import nl.Inholland.exceptions.IbanAlreadyExistsException;
import nl.Inholland.exceptions.InvalidAccountTypeException;
import nl.Inholland.exceptions.SavingsAccountAlreadyExistsException;
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
            do {
                newIban = IbanGenerator.makeIban(CountryCodeEnum.valueOf(request.getCountryCode()), BankCodeEnum.valueOf(request.getBank()));
            }while(ibanRepo.existsById(newIban.getIbanCode()));
        }else {
            newIban = IbanGenerator.makeIban(CountryCodeEnum.valueOf(request.getCountryCode()), BankCodeEnum.valueOf(request.getBank()), request.getBban());
            if(ibanRepo.existsById(newIban.getIbanCode())) throw new IbanAlreadyExistsException("Iban is already in use");
        }

        User activeUser = userRepo.getUserByUsername(request.getUser());

        switch (request.getType().toLowerCase()){
            case "current":
                accountFactory = new CurrentAccountFactory();
                if(activeUser.getIbanList().get(AccountType.Current) == null) {
                    activeUser.addIban(AccountType.Current, newIban);
                }else{
                    throw new CurrentAccountAlreadyExistsException("This user already owns an account of type current ");
                }
                break;
            case "savings":
                accountFactory = new SavingsAccountFactory();
                if(activeUser.getIbanList().get(AccountType.Savings) == null) {
                    activeUser.addIban(AccountType.Savings, newIban);
                }else{
                    throw new SavingsAccountAlreadyExistsException("This user already owns an account of type savings ");
                }
                break;
            default:
                throw new InvalidAccountTypeException("Type of account not recognized");
        }

            Account newAccount = accountFactory.createAccount(request);
            newAccount.setIban(newIban);

            accoRepo.save(newAccount);
            userRepo.save(activeUser);

            vault.increaseBalance(newAccount.getBalance().getAmount());
    }

    // NOT TESTED
    public List<Account> getUserRelatedAccounts(String username){
        List<Account> accounts = new ArrayList<>();
        User activeUser;
        try{
            activeUser = userRepo.getUserByUsername(username);
        }catch (Exception exp){
            throw new NoSuchElementException();
        }
        for (Map.Entry<AccountType, Iban> entry : activeUser.getIbanList().entrySet()) {
            accounts.add(accoRepo.getAccountByIban(entry.getValue()));
        }
        return accounts;
    }

    private boolean ibanExists(Iban iban){
        try{
            ibanRepo.getOne(iban.getIbanCode());

        }catch(NullPointerException e){
            return false;
        }
        return true;
    }


    public void deleteAccount(Long id) {
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
