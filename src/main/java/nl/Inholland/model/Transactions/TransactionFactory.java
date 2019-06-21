package nl.Inholland.model.Transactions;

import nl.Inholland.model.requests.TransactionRequest;

public interface TransactionFactory {
    Transaction createTransaction(TransactionRequest request);
}
