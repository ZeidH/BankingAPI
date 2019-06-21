package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.TransactionSpecification;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccountFactory;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Accounts.SavingsAccountFactory;
import nl.Inholland.model.Transactions.*;
import nl.Inholland.model.requests.TransactionRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransactionService extends AbstractService {

    private TransactionFactory transactionFactory;

    private ExecutorService service = Executors.newCachedThreadPool();

    public TransactionService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
    }

    public void createTransaction(TransactionRequest request) throws Exception{

        Transaction newTransaction;

        Iban creator = ibanRepo.getOne(request.getCreator());

        // ADD CONCURRENCY

        switch (request.getType().toLowerCase()){
            case "flow":

                Iban sender = ibanRepo.getOne(request.getSender());
                Iban receiver = ibanRepo.getOne(request.getReceiver());

                transactionFactory = new TransactionFlowFactory(creator, sender, receiver);
                break;
            case "withdrawal":
                transactionFactory = new WithdrawalFactory(creator);
                break;
            case "deposit":
                transactionFactory = new DepositFactory(creator);
                break;
            default:
                throw new Exception();
        }

        newTransaction = transactionFactory.createTransaction(request);

        if(newTransaction.equals(null)) System.out.println("aqui");


        tranRepo.save(newTransaction);
       // attachTransactionToAccount(creator.getAccount(), newTransaction);
    }

    private void attachTransactionToAccount(Account account, Transaction transaction){
        Account accountToUpdate = accoRepo.getOne(account.getId());
        accountToUpdate.addTransaction(transaction);
        accoRepo.save(account);

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
        /*
        Account senderAccount = accountService.getAccountByIban(sender.getIbanCode());
        Account receiverAccount = accountService.getAccountByIban(receiver.getIbanCode());

        if(senderAccount.getClass().equals(SavingsAccount.class) && (receiverAccount.getIban().getIbanCode() != senderAccount.getIban().getIbanCode())) return false;
        */
        return true;
    }

    public List<Transaction> getTransactionsFromAccount(Long id) {
        return accoRepo.getOne(id).getTransactions();
    }
}
