package nl.Inholland.model.Transactions;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.model.Accounts.Iban;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
}
