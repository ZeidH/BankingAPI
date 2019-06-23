package nl.Inholland.service;

import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccount;
import nl.Inholland.model.Accounts.VaultAccount;
import nl.Inholland.model.Transactions.Transaction;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VaultService extends AbstractService implements VaultObserver {

    private Account vault = VaultAccount.getVaultInstance();

    public VaultService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        accoRepo.save(vault);
    }

    @Override
    public void increaseBalance(BigDecimal amount) {
        vault.getBalance().increaseBalance(amount);
        accoRepo.save(vault);
    }

    @Override
    public void decreaseBalance(BigDecimal amount) {
        vault.getBalance().decreaseBalance(amount);
        accoRepo.save(vault);
    }

    @Override
    public void attachTransaction(Transaction transaction){
        vault.addTransaction(transaction);
        accoRepo.save(vault);
    }
}
