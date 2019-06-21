package nl.Inholland.model.Accounts;

import nl.Inholland.enumerations.AccountStatusEnum;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class VaultAccount extends Account{

    private static VaultAccount vaultInstance;

    private VaultAccount() {
        this.setName("Bank");
        this.setStatus(AccountStatusEnum.OPEN);
        this.setBalance(new Balance(new BigDecimal(0.0)));
       // this.setIban(new Iban());
    }

    public static VaultAccount getVaultInstance() {
        if(vaultInstance == null){
            vaultInstance = new VaultAccount();
        }
        return vaultInstance;
    }
}
