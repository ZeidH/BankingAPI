package nl.Inholland.model.Accounts;

import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.requests.AccountRequest;

import java.math.BigDecimal;

public class CurrentAccountFactory implements AccountFactory {

    @Override
    public Account createAccount(AccountRequest request) {

        Account account = new CurrentAccount();
        account.setBalance(new Balance(new BigDecimal(request.getBalance())));
        account.setName(request.getName());
        account.setStatus(AccountStatusEnum.OPEN);

        return account;
    }
}
