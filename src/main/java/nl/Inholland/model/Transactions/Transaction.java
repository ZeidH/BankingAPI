package nl.Inholland.model.Transactions;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Users.User;

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

    public Transaction(BigDecimal amount, String currency, CategoryEnum category, StatusEnum status, String dateCreated, User creator) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transId_seq")
    @Column(name = "transaction_id")
    private Long id;


    private BigDecimal amount;
    private String currency;
    private CategoryEnum category;
    private StatusEnum status;
    private String dateCreated;


    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Transaction(BigDecimal amount, String currency, User creator, CategoryEnum category, StatusEnum status, String dateCreated) {
        this.amount = amount;
        this.currency = currency;
        this.creator = creator;
        this.category = category;
        this.status = status;
        this.dateCreated = dateCreated;
    }

}
