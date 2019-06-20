package nl.Inholland.model.Accounts;

import java.math.BigDecimal;

public class BalanceDecrease implements BalanceBehaviour {
    @Override
    public BigDecimal updateBalance(Account account, BigDecimal amount) {
        BigDecimal updatedBalance = account.getBalance().subtract(amount);
        return updatedBalance;
    }
}
