package io.swagger.service;

import java.math.BigDecimal;

public interface VaultObserver {
    void addBalance(BigDecimal amount);
    void substractBalance(BigDecimal amount);
}
