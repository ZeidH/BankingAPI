package io.swagger.model;

import java.math.BigDecimal;

public interface BalanceBehaviour {
    BigDecimal updateBalance(Account account, BigDecimal amount);
}
