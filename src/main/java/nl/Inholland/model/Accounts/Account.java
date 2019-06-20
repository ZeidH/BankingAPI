package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.Transactions.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "account_seq", initialValue = 0, allocationSize=1)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    private Long id;
    private BigDecimal balance;
    private String name;
    private AccountStatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iban_account", referencedColumnName = "ibanCode")
    protected Iban iban;

    @OneToMany
    private List<Transaction> transactions;

    public Account(String name,BigDecimal balance, Iban iban, AccountStatusEnum status) {
        this.balance = balance;
        this.name = name;
        this.status = status;
        this.iban = iban;
        this.transactions = transactions;
    }
}



