package nl.Inholland.model.Accounts;


import java.math.BigDecimal;

public interface BalanceBehaviour {
    BigDecimal updateBalance(Account account, BigDecimal amount);
}
