package io.swagger.service;

import io.swagger.QueryBuilder.*;
import io.swagger.QueryBuilder.Specifications.TransactionSpecification;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService extends AbstractService {
    @Autowired
    private TransactionRepository repo;

    //new Transaction(new BigDecimal("60.10"),"EUR", "NL02INGB0154356789", CategoryEnum.ENTERTAINMENT, "NL02INGB0154356789", "NL02INGB0153457789", "12-05-2019 22:24:10", StatusEnum.PROCESSED)

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public void createTransaction(Transaction transaction) {
        repo.save(transaction);
    }

    public List<Transaction> getTransactions(String search) {
        Specification<Transaction> spec = getBuilder(search).build(searchCriteria -> new TransactionSpecification((SpecSearchCriteria) searchCriteria));
        return repo.findAll(spec);
    }

    public void updateStatus(Long id, Transaction.StatusEnum status) {
        Transaction transaction = repo.getOne(id);
        transaction.setStatus(status);
        repo.save(transaction);
    }
}
