package nl.Inholland.model.Accounts;

import java.math.BigDecimal;

public class BalanceIncrease implements BalanceBehaviour {

    @Override
    public BigDecimal updateBalance(Account account, BigDecimal amount) {
        BigDecimal updatedBalance = account.getBalance().add(amount);
        return updatedBalance;
    }
}
