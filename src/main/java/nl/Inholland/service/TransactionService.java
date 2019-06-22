package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.TransactionSpecification;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.*;
import nl.Inholland.model.Transactions.*;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.TransactionRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.omg.CORBA.Request;
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

        User creator = userRepo.getUserByUsername(request.getCreator());



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

        Transaction newTransaction = transactionFactory.createTransaction(request);
        tranRepo.save(newTransaction);

        // ADD CONCURRENCY

        performTransactionFlow((TransactionFlow) newTransaction);


    }

    private void performTransactionFlow(TransactionFlow newTransaction) throws Exception{

        if(!sufficientFunds(newTransaction.getSender(), newTransaction.getAmount())) throw new Exception();
        if(!doesNotSurpassDailyLimit(newTransaction.getSender())) throw new Exception();
        if(notSendingFromSavingsToThirdParty(newTransaction.getSender(), newTransaction.getReceiver())) throw new Exception();

        attachTransactionToAccount(newTransaction.getSender(), newTransaction);
        attachTransactionToAccount(newTransaction.getReceiver(), newTransaction);
        updateBalanceSenderReceiver(newTransaction.getSender(), newTransaction.getReceiver(), newTransaction.getAmount());

        updateStatus(newTransaction.getId(), StatusEnum.PROCESSED);

    }

    private void attachTransactionToAccount(Iban account, Transaction transaction){
        Account accountToUpdate = accoRepo.getAccountByIban(account);
        accountToUpdate.addTransaction(transaction);
        accoRepo.save(accountToUpdate);
    }

    private void updateBalanceSenderReceiver(Iban senderIban, Iban receiverIban, BigDecimal amount){
        Account sender = accoRepo.getAccountByIban(senderIban);
        Account receiver = accoRepo.getAccountByIban(receiverIban);

        sender.getBalance().decreaseBalance(amount);
        receiver.getBalance().increaseBalance(amount);

        accoRepo.save(sender);
        accoRepo.save(receiver);

    }

    /*
    private void updateParticipantsBalance(Iban senderIban, Iban receiverIban, BigDecimal amount) throws Exception{

        switch (request.getType().toLowerCase()){
            case "flow": {
                Account sender = accoRepo.getAccountByIban(senderIban);
                Account receiver = accoRepo.getAccountByIban(receiverIban);

                sender.getBalance().decreaseBalance(amount);
                receiver.getBalance().increaseBalance(amount);

                accoRepo.save(sender);
                accoRepo.save(receiver);

                System.out.println(sender.getBalance().getAmount().toString());
                System.out.println(receiver.getBalance().getAmount().toString());

                break;
            }
            case "withdrawal": {
                Account creator = accoRepo.getAccountByIban(ibanRepo.getOne(request.getCreator()));
                creator.getBalance().decreaseBalance(new BigDecimal(request.getAmount()));
                accoRepo.save(creator);
                break;
            }
            case "deposit": {
                Account creator = accoRepo.getAccountByIban(ibanRepo.getOne(request.getCreator()));
                creator.getBalance().increaseBalance(new BigDecimal(request.getAmount()));
                accoRepo.save(creator);
                break;
            }
            default:
                throw new Exception();
        }

    }*/


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

    private boolean notSendingFromSavingsToThirdParty(Iban sender, Iban receiver){
        //if its a savings account
        if(accoRepo.getAccountByIban(sender).getClass().equals(CurrentAccount.class)){
            User activeUser = userRepo.findByIbanList(sender);
            if(receiver.getIbanCode() != activeUser.getIbanList().get(AccountType.Current).getIbanCode()){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    private boolean bothAccountsActive(Iban sender, Iban receiver){
        Account senderAccount = accoRepo.getAccountByIban(sender);
        Account receiverAccount = accoRepo.getAccountByIban(receiver);

        if(senderAccount.getStatus() == AccountStatusEnum.ClOSED || receiverAccount.getStatus() == AccountStatusEnum.ClOSED) return false;

        return true;
    }

    private boolean sufficientFunds(Iban sender, BigDecimal amount){
        Account senderAccount = accoRepo.getAccountByIban(sender);
        if(senderAccount.getBalance().getAmount().compareTo(amount) == 1) return true;
        return false;
    }

    private boolean doesNotSurpassDailyLimit(Iban sender){
        return true;
    }


    public List<Transaction> getTransactionsFromAccount(Long id) {
        return accoRepo.getOne(id).getTransactions();
    }
}
