package nl.Inholland.service;

import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.BalanceBehaviour;
import nl.Inholland.model.Accounts.BalanceDecrease;
import nl.Inholland.model.Accounts.BalanceIncrease;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VaultService extends AbstractService {

    public BalanceBehaviour balanceBehaviour;
    private Account vault;


    public VaultService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        this.balanceBehaviour = new BalanceIncrease();
    }


    public void addBalance(BigDecimal amount) {
        vault = accoRepo.getOne(new Long(0));
        this.balanceBehaviour = new BalanceIncrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accoRepo.save(vault);
    }

    public void substractBalance(BigDecimal amount) {
        vault = accoRepo.getOne(new Long(0));
        this.balanceBehaviour = new BalanceDecrease();
        BigDecimal newBalance = this.balanceBehaviour.updateBalance(vault, amount);
        vault.setBalance(newBalance);
        accoRepo.save(vault);
    }
}
