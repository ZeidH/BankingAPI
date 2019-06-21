package nl.Inholland.service;

import java.math.BigDecimal;

public interface VaultObserver {
    void increaseBalance(BigDecimal amount);
    void decreaseBalance(BigDecimal amount);

}
