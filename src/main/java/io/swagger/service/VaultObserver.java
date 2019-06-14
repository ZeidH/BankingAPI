package io.swagger.service;

import java.math.BigDecimal;

public interface VaultObserver {
    void updateBalance(BigDecimal amount);
}
