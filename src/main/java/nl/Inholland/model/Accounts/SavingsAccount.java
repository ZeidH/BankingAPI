package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class SavingsAccount extends Account{

    private BigDecimal interestRate = new BigDecimal(0.10);

    @OneToOne
    private Account ownerAccount;

    public SavingsAccount(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
        this.iban = ownerAccount.getIban();
    }

}
