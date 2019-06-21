package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.Transactions.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
@SequenceGenerator(name = "account_seq", initialValue = 0, allocationSize=1)
@Getter
@Setter
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @Column(name = "account_id")
    private Long id;

    private String name;
    private AccountStatusEnum status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ibanCode", referencedColumnName = "ibanCode")
    private Iban iban;

    @OneToOne(mappedBy = "account")
    private Balance balance;

    @ManyToMany
    @JoinTable(
            name = "account_has_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String name,Balance balance, AccountStatusEnum status) {
        //this.balance = balance;
        this.name = name;
        this.status = status;
        this.transactions = transactions;
    }
}