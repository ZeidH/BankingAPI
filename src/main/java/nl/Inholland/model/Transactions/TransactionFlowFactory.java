package nl.Inholland.model.Transactions;

import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.requests.TransactionRequest;

import java.math.BigDecimal;

public class TransactionFlowFactory implements TransactionFactory {

    private Iban creator;
    private Iban sender;
    private Iban receiver;

    public TransactionFlowFactory(Iban creator, Iban sender, Iban receiver) {
        this.creator = creator;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public Transaction createTransaction(TransactionRequest request) {

        TransactionFlow transaction = new TransactionFlow();

        transaction.setCreator(creator);
        transaction.setAmount(new BigDecimal(request.getAmount()));
        transaction.setStatus(StatusEnum.PENDING);

        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        transaction.addAccount(sender.getAccount());
        transaction.addAccount(receiver.getAccount());
        transaction.addAccount(creator.getAccount());

        return transaction;
    }
}
