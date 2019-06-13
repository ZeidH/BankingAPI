package io.swagger.model;

import io.swagger.service.TransactionObservable;
import io.swagger.service.VaultObserver;

import java.math.BigDecimal;

public class Process implements ProcessObserver {

    private  VaultObserver vault;
    private TransactionObservable transactionObservable;
    private Transaction transaction;

    public Process(VaultObserver vault, TransactionObservable transactionObservable, Transaction transaction) {
        this.vault = vault;
        this.transactionObservable = transactionObservable;
        this.transaction = transaction;

        this.transactionObservable.registerProcess(this);
    }

    public void updateStatus(){
        this.transactionObservable.updateStatus();
    }

    @Override
    public void updateBalance(BigDecimal balance) {
        vault.updateBalance(balance);
        updateStatus();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
