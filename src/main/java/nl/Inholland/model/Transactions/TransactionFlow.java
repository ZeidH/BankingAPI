package nl.Inholland.model.Transactions;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Users.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;

@SequenceGenerator(name = "transId_seq", initialValue = 10000001)
@NoArgsConstructor
@Entity
@Data
public class TransactionFlow extends Transaction{

    @ManyToOne
    @JoinColumn(name = "sender")
    private Iban sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Iban receiver;

    public Iban getSender() {
        return sender;
    }

    public void setSender(Iban sender) {
        this.sender = sender;
    }

    public Iban getReceiver() {
        return receiver;
    }

    public void setReceiver(Iban receiver) {
        this.receiver = receiver;
    }

    public TransactionFlow(BigDecimal amount, String currency, User creator, CategoryEnum category, StatusEnum status, String dateCreated, Iban sender, Iban receiver) {
        super(amount, currency, creator, category, status, dateCreated);
        this.sender = sender;
        this.receiver = receiver;
    }
}
