package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.AccountSpecification;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AccountService extends AbstractService {


    private ExecutorService service = Executors.newCachedThreadPool();

    public AccountService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
    }

    public List<Account> getAccounts(String search) {
        Specification<Account> spec = getBuilder(search).build(searchCriteria -> new AccountSpecification((SpecSearchCriteria) searchCriteria));
        return accoRepo.findAll(spec);
    }

    public void registerAccount(Account account) {
        /*
        do{
            account.getIban().buildIban();
        }while(ibanRepo.existsByIbanCode(account.getIban().getIbanCode()));

        account.setStatus(AccountStatusEnum.OPEN);

        vault.addBalance(account.getBalance());
        accoRepo.save(account);*/
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

    public Account getAccountByIban(String iban) {
     //   Account account = accoRepo.getAccountByIban(iban);
        if(null != null){
            return null;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void balanceUpdate(Transaction transaction){
        /*
        Account senderAccount = getAccountByIban(transaction.getSender().getIbanCode());
        Account receiverAccount = getAccountByIban(transaction.getReceiver().getIbanCode());

        this.balanceBehaviour = new BalanceDecrease();
        senderAccount.setBalance(this.balanceBehaviour.updateBalance(senderAccount, transaction.getAmount()));
        accoRepo.save(senderAccount);

        this.balanceBehaviour = new BalanceIncrease();
        receiverAccount.setBalance(this.balanceBehaviour.updateBalance(senderAccount, transaction.getAmount()));
        accoRepo.save(receiverAccount);
        */
    }

    public boolean bothAccountsActive(Transaction transaction){
/*
        Account senderAccount = getAccountByIban(transaction.getSender().getIbanCode());
        Account receiverAccount = getAccountByIban(transaction.getReceiver().getIbanCode());

        if(senderAccount.getStatus() == AccountStatusEnum.ClOSED || receiverAccount.getStatus() == AccountStatusEnum.ClOSED) return false;
*/
        return true;
    }

    public boolean sufficientFunds(Transaction transaction){
/*
        Account senderAccount = getAccountByIban(transaction.getSender().getIbanCode());
        Account receiverAccount = getAccountByIban(transaction.getReceiver().getIbanCode());

        if(senderAccount.getBalance().compareTo(transaction.getAmount()) == 1) return true;
*/
        return false;
    }




}
