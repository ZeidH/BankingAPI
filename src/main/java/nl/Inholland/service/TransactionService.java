package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.TransactionSpecification;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.exceptions.*;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Transaction service covers transactions to be performed
 * Inherits AbstractService for access to all repositories and specification builder
 * Checks if requirements for transactions are met and changes transactions status if failed or processed
 */

@Service
public class TransactionService extends AbstractService implements VaultSubject {

    private TransactionFactory transactionFactory;

    private ExecutorService service = Executors.newCachedThreadPool();

    public TransactionService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
    }

    @Override
    public void registerVault(VaultObserver vault) {
        this.vault = vault;
    }

    @Async("TransactionTaskExecutor")
    @Transactional
    public void createTransactionFlow(TransactionRequest request) throws Exception, NullPointerException{

        User creator = userRepo.getUserByUsername(request.getCreator());
        Iban sender = ibanRepo.getOne(request.getSender());
        Iban receiver = ibanRepo.getOne(request.getReceiver());
        transactionFactory = new TransactionFlowFactory(creator, sender, receiver);
        Transaction newTransaction = transactionFactory.createTransaction(request);
        tranRepo.save(newTransaction);

        try{
            performTransactionFlow((TransactionFlow) newTransaction);
        }catch (Exception e){
            updateStatus(newTransaction.getId(), StatusEnum.FAILED);
            throw e;
        }

    }

    private void performTransactionFlow(TransactionFlow newTransaction) throws Exception{

        if(sendingToOutsideBank(newTransaction.getReceiver())){
            vault.attachTransaction(newTransaction);
            vault.decreaseBalance(newTransaction.getAmount());
            decreaseIndividual(newTransaction.getSender(), newTransaction.getAmount());
            attachTransactionToAccount(newTransaction.getSender(), newTransaction);
        }else{
            if(!bothAccountsActive(newTransaction.getSender(), newTransaction.getReceiver())) throw new ReceiverNotActiveException("One or all the accounts participant of this transaction are not active");
            if(!sufficientFunds(newTransaction.getSender(), newTransaction.getAmount())) throw new NotEnoughFundsException("Sender does not posses enough balance to perform this transaction");
            if(!doesNotSurpassDailyLimit(newTransaction.getSender())) throw new DailyLimitSurpassedException("This action does not obay daily limit");
            if(!notSendingFromSavingsToThirdParty(newTransaction.getSender(), newTransaction.getReceiver())) throw new SendingFromSavingsToThirdPartyException("Savings accounts can only send to their owner's current account");
            if(!ibanExists(newTransaction.getSender())) throw new AccountDoesNotExistException("Sender is not known within this domain");

            attachTransactionToAccount(newTransaction.getSender(), newTransaction);
            attachTransactionToAccount(newTransaction.getReceiver(), newTransaction);
            updateBalanceSenderReceiver(newTransaction.getSender(), newTransaction.getReceiver(), newTransaction.getAmount());
        }
        updateStatus(newTransaction.getId(), StatusEnum.PROCESSED);
    }

    private void attachTransactionToAccount(Iban account, Transaction transaction){
        Account accountToUpdate = accoRepo.getAccountByIban(account);
        accountToUpdate.addTransaction(transaction);
        accoRepo.save(accountToUpdate);
    }

    private void decreaseIndividual(Iban senderIban, BigDecimal amount){
        Account sender = accoRepo.getAccountByIban(senderIban);
        sender.getBalance().decreaseBalance(amount);
        accoRepo.save(sender);
    }

    private void updateBalanceSenderReceiver(Iban senderIban, Iban receiverIban, BigDecimal amount){
        Account sender = accoRepo.getAccountByIban(senderIban);
        Account receiver = accoRepo.getAccountByIban(receiverIban);

        sender.getBalance().decreaseBalance(amount);
        receiver.getBalance().increaseBalance(amount);

        accoRepo.save(sender);
        accoRepo.save(receiver);
    }

    public List<Transaction> getTransactions(String search) {
        Specification<Transaction> spec = getBuilder(search).build(searchCriteria -> new TransactionSpecification((SpecSearchCriteria) searchCriteria));
        return tranRepo.findAll(spec);
    }



    private void updateStatus(Long id, StatusEnum status) {
        Transaction transaction = getTransaction(id);
        transaction.setStatus(status);
        tranRepo.save(transaction);
    }

    public Transaction getTransaction(Long id){
        return tranRepo.getOne(id);
    }

    private boolean ibanExists(Iban iban){
        try{
            ibanRepo.getOne(iban.getIbanCode());
            return true;
        }catch(NullPointerException e){
            return false;
        }
    }

    private boolean notSendingFromSavingsToThirdParty(Iban sender, Iban receiver){
        //if its a savings account
        if(accoRepo.getAccountByIban(sender).getClass().equals(SavingsAccount.class)){
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

    private boolean sendingToOutsideBank(Iban receiver){
        if(!receiver.getBank().toString().equals(BankCodeEnum.INHO.toString())) return true;
        return false;
    }

    private boolean doesNotSurpassDailyLimit(Iban sender){
        return true;
    }


    public List<Transaction> getTransactionsFromAccount(String id) {
        Iban iban = ibanRepo.findById(id).orElseThrow(NoSuchElementException::new);
        return accoRepo.getAccountByIban(iban).getTransactions();
    }
}
