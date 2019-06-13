package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repo;
    private boolean sorted;
    private int entries;
    private Date dateFrom;
    private Date dateTo;

    //new Transaction(new BigDecimal("60.10"),"EUR", "NL02INGB0154356789", CategoryEnum.ENTERTAINMENT, "NL02INGB0154356789", "NL02INGB0153457789", "12-05-2019 22:24:10", StatusEnum.PROCESSED)

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setSorting(boolean sorted){
        this.sorted = sorted;
    }


    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public void createTransaction(Transaction transaction){
        repo.save(transaction);
    }

    public List<Transaction> getTransaction() {
        return repo.findAll();
//        if (sorted) {
//            transactions = transactions.stream().sorted().collect(Collectors.toList());
//        }
//        if (entries > 0) {
//            transactions = new ArrayList<>(transactions.subList(0, entries));
//        }
//        return transactions;
    }

//    public Transaction getTransaction(int id){
//        for(Transaction transaction : transactions){
//            if(transaction.getId() == id){
//                return transaction;
//            }
//        }
//        throw new NoSuchElementException();
//    }
}
