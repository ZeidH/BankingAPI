package io.swagger.service;

import io.swagger.QueryBuilder.*;
import io.swagger.QueryBuilder.Specifications.TransactionSpecification;
import io.swagger.model.Process;
import io.swagger.model.ProcessObserver;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.IbanRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class TransactionService extends AbstractService implements  TransactionObservable {

    private ExecutorService service = Executors.newCachedThreadPool();

    private VaultObserver vault;
    private ProcessObserver process;

    //new Transaction(new BigDecimal("60.10"),"EUR", "NL02INGB0154356789", CategoryEnum.ENTERTAINMENT, "NL02INGB0154356789", "NL02INGB0153457789", "12-05-2019 22:24:10", StatusEnum.PROCESSED)

    public TransactionService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super( userRepo,  tranRepo,  accoRepo,  ibanRepo);
    }

    public void createTransaction(Transaction transaction){

        ProcessObserver process = new Process(vault, this, transaction);
  //      process.updateBalance(transaction.getAmount());
        insertTransaction(transaction);
    }

    public void insertTransaction(Transaction transaction){
        tranRepo.save(transaction);
    }

    public List<Transaction> getTransactions(String search) {
        Specification<Transaction> spec = getBuilder(search).build(searchCriteria -> new TransactionSpecification((SpecSearchCriteria) searchCriteria));
        return tranRepo.findAll(spec);
    }
    public List<Transaction> getAuthenticatedTransactions(long accountId){
        return accoRepo.getOne(accountId).getTransactions();
    }

    public void updateStatus(Long id, Transaction.StatusEnum status) {
        Transaction transaction = tranRepo.getOne(id);
        transaction.setStatus(status);
        tranRepo.save(transaction);
    }
    @Override
    public void updateStatus() {

    }

    @Override
    public void registerVault(VaultObserver vault) {
        this.vault = vault;
    }

    @Override
    public void registerProcess(ProcessObserver processObserver) {
        this.process = processObserver;
    }

}
