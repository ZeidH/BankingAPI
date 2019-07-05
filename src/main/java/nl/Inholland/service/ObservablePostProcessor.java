package nl.Inholland.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * ObservablePostProcessor observes made transactions
 */

@Component
@Scope("prototype")
public class ObservablePostProcessor implements BeanPostProcessor {

    private final VaultSubject transactionService;
    private final VaultSubject accountService;
    private final VaultObserver vaultService;

    public ObservablePostProcessor(VaultSubject transactionService, VaultSubject accountService, VaultObserver vaultService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.vaultService = vaultService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        // NOP
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        configureObservers(transactionService, accountService, vaultService);
        // leave untouched
        return o;
    }

    private void configureObservers(VaultSubject transactionService, VaultSubject accountService, VaultObserver vaultService){
        transactionService.registerVault(vaultService);
        accountService.registerVault(vaultService);
    }
}