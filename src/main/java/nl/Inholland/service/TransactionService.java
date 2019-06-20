package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.TransactionSpecification;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Accounts.SavingsAccount;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransactionService extends AbstractService{

    @Autowired
    private AccountService accountService;
    @Autowired
    private VaultService vaultService;

    private ExecutorService service = Executors.newCachedThreadPool();

    public TransactionService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
    }

    public void createTransaction(Transaction transaction){

        insertTransaction(transaction);

        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    if(transaction.getReceiver().BANK != "INHO") vaultService.substractBalance(transaction.getAmount());
                    accountService.balanceUpdate(transaction);
                    transaction.setStatus(StatusEnum.PROCESSED);
                    tranRepo.save(transaction);

                }catch(Exception e){
                    transaction.setStatus(StatusEnum.FAILED);
                }
            }
        });
    }

    public void insertTransaction(Transaction transaction){
        tranRepo.save(transaction);
    }

    public List<Transaction> getTransactions(String search) {
        Specification<Transaction> spec = getBuilder(search).build(searchCriteria -> new TransactionSpecification((SpecSearchCriteria) searchCriteria));
        return tranRepo.findAll(spec);
    }


    @Transactional
    public void updateStatus(Long id, StatusEnum status) {
        Transaction transaction = getTransaction(id);
        transaction.setStatus(status);
        tranRepo.save(transaction);
    }

    public Transaction getTransaction(Long id){
        return tranRepo.getOne(id);
    }

    public boolean notSendingFromSavingsToThirdParty(Iban sender, Iban receiver){

        Account senderAccount = accountService.getAccountByIban(sender.getIbanCode());
        Account receiverAccount = accountService.getAccountByIban(receiver.getIbanCode());

        if(senderAccount.getClass().equals(SavingsAccount.class) && (receiverAccount.getIban().getIbanCode() != senderAccount.getIban().getIbanCode())) return false;

        return true;
    }

    public List<Transaction> getTransactionsFromAccount(Long id) {
        return accoRepo.getOne(id).getTransactions();
    }
}
