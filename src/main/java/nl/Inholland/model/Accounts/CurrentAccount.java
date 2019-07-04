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
    private BigDecimal dailyLimit;

    public CurrentAccount(String name, AccountStatusEnum status, Iban iban, Balance balance, BigDecimal dailyLimit) {
        super(name, status, iban, balance);
        this.dailyLimit = dailyLimit;
    }
}
