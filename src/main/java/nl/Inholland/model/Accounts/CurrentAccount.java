package nl.Inholland.model.Accounts;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.AccountStatusEnum;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class CurrentAccount extends Account {
    private BigDecimal transactionLimit = new BigDecimal(1000);
    private BigDecimal dailyLimit = new BigDecimal(1000);

    public CurrentAccount(String name, BigDecimal balance, Iban iban, AccountStatusEnum status) {
        super(name, balance, iban, status);
    }
}
