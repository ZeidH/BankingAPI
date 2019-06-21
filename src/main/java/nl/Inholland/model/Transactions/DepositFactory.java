package nl.Inholland.model.Transactions;

import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.requests.TransactionRequest;

import java.math.BigDecimal;

public class DepositFactory implements TransactionFactory {

    private Iban creator;

    public DepositFactory(Iban creator) {
        this.creator = creator;
    }

    @Override
    public Transaction createTransaction(TransactionRequest request) {
        Deposit transaction = new Deposit();

        transaction.setCreator(creator);
        transaction.setAmount(new BigDecimal(request.getAmount()));
        transaction.setStatus(StatusEnum.PENDING);


        return transaction;
    }
}
