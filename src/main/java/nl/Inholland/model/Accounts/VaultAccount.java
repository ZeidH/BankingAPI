package nl.Inholland.model.Accounts;

import javax.persistence.Entity;

@Entity
public class VaultAccount extends Account{

    private static VaultAccount vaultInstance;

    private VaultAccount() {
    }

    public static VaultAccount getVaultInstance() {
        if(vaultInstance == null){
            vaultInstance = new VaultAccount();
        }
        return vaultInstance;
    }
}
