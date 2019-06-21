package nl.Inholland.service;

import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.VaultAccount;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VaultService extends AbstractService implements VaultObserver {

    private Account vault = VaultAccount.getVaultInstance();

    public VaultService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        super.registerVault(this);
    }


    @Override
    public void increaseBalance(BigDecimal amount) {
        accoRepo.getOne(vault.getId());
    }

    @Override
    public void decreaseBalance(BigDecimal amount) {

    }
}
