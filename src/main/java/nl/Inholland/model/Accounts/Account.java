package nl.Inholland.model.Accounts;

import com.sun.istack.Nullable;
import lombok.*;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.Transactions.Transaction;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
@SequenceGenerator(name = "account_seq", initialValue = 1, allocationSize=1)
public abstract class Account {

    public Account(String name, AccountStatusEnum status, Iban iban, Balance balance) {
        this.name = name;
        this.status = status;
        this.iban = iban;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @Column(name = "account_id")
    @Setter(AccessLevel.NONE)
    protected Long id;

    protected String name;
    protected AccountStatusEnum status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iban", nullable = false)
    private Iban iban;


    @OneToOne(cascade = CascadeType.ALL)
    protected Balance balance;

    @ManyToMany
    @JoinTable(
            name = "account_has_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    protected List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

}