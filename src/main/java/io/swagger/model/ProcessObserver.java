package io.swagger.model;

import java.math.BigDecimal;

public interface ProcessObserver {
    void updateVault(BigDecimal balance);
}
