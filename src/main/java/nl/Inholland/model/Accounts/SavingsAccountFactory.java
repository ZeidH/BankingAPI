package nl.Inholland.model.Accounts;

import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.requests.AccountRequest;

import java.math.BigDecimal;

public class SavingsAccountFactory implements AccountFactory {

    @Override
    public Account createAccount(AccountRequest request) {
        SavingsAccount account = new SavingsAccount();
        account.setName(request.getName());
        account.setBalance(new Balance(new BigDecimal(request.getBalance())));
        account.setInterestRate(new BigDecimal(request.getInterestRate()));
        account.setStatus(AccountStatusEnum.OPEN);

        return account;
    }
}
