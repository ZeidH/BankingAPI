package nl.Inholland.model.Transactions;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Iban;

import javax.persistence.*;
import java.math.BigDecimal;

@SequenceGenerator(name = "transId_seq", initialValue = 10000001)
@NoArgsConstructor
@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transId_seq")
    private Long id;
    private BigDecimal amount;
    private String currency;
    private CategoryEnum category;
    private StatusEnum status;
    private String dateCreated;

    @ManyToOne
    @JoinColumn(name = "creator")
    private Iban creator;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Iban sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Iban receiver;

    public Transaction(BigDecimal amount, String currency, Iban creator, CategoryEnum category, StatusEnum status, String dateCreated, Iban sender, Iban receiver) {
        this.amount = amount;
        this.currency = currency;
        this.creator = creator;
        this.category = category;
        this.status = status;
        this.dateCreated = dateCreated;
        this.sender = sender;
        this.receiver = receiver;
    }
}
