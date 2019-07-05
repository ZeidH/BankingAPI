package nl.Inholland.service;

import nl.Inholland.model.Transactions.Transaction;

import java.math.BigDecimal;

public interface VaultObserver {
    //Uses BigDecimal to ensure calculations with money are not rounded up or down
    void increaseBalance(BigDecimal amount);
    void decreaseBalance(BigDecimal amount);
    void attachTransaction(Transaction transaction);

}
