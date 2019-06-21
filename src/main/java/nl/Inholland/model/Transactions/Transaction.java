package nl.Inholland.model.Transactions;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.Iban;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@SequenceGenerator(name = "transId_seq", initialValue = 10000001)
@NoArgsConstructor
@Entity
@Data
@Table(name = "Transactions")
@Getter
@Setter
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transId_seq")
    @Column(name = "transaction_id")
    private Long id;
    private BigDecimal amount;
    private String currency;
    private CategoryEnum category;
    private StatusEnum status;
    private String dateCreated;

    @ManyToMany(mappedBy = "transactions")
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "creator")
    private Iban creator;

    public Transaction(BigDecimal amount, String currency, Iban creator, CategoryEnum category, StatusEnum status, String dateCreated) {
        this.amount = amount;
        this.currency = currency;
        this.creator = creator;
        this.category = category;
        this.status = status;
        this.dateCreated = dateCreated;
    }
}
