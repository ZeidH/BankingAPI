package nl.Inholland.model.Transactions;

import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.requests.TransactionRequest;

import java.math.BigDecimal;

public class WithdrawalFactory implements TransactionFactory {

    private Iban creator;

    public WithdrawalFactory(Iban creator) {
        this.creator = creator;
    }

    @Override
    public Transaction createTransaction(TransactionRequest request) {
        Withdrawal transaction = new Withdrawal();

        transaction.setCreator(creator);
        transaction.setAmount(new BigDecimal(request.getAmount()));
        transaction.setStatus(StatusEnum.PENDING);



        return transaction;
    }
}
