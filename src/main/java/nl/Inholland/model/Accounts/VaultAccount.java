package nl.Inholland.model.Accounts;

import nl.Inholland.enumerations.AccountStatusEnum;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class VaultAccount extends Account{
    public VaultAccount(String name, BigDecimal balance, Iban iban, AccountStatusEnum status) {
        super(name, balance, iban, status);
    }
}