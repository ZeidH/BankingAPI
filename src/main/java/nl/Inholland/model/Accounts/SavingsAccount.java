package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.AccountStatusEnum;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class SavingsAccount extends Account{

    private BigDecimal interestRate = new BigDecimal(0.10);

    public SavingsAccount(String name, AccountStatusEnum status, Iban iban, Balance balance, BigDecimal interestRate) {
        super(name, status, iban, balance);
        this.interestRate = interestRate;
    }
}
