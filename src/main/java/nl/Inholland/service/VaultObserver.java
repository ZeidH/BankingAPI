package nl.Inholland.service;

import nl.Inholland.model.Transactions.Transaction;

import java.math.BigDecimal;

public interface VaultObserver {
    void increaseBalance(BigDecimal amount);
    void decreaseBalance(BigDecimal amount);
    void attachTransaction(Transaction transaction);

}
