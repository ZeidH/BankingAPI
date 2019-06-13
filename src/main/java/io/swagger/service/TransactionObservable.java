package io.swagger.service;

import io.swagger.model.ProcessObserver;

public interface TransactionObservable {
    void updateStatus();
    void registerVault(VaultObserver vault);
    void registerProcess(ProcessObserver processObserver);
}
