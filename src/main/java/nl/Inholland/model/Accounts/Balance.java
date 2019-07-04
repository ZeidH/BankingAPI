package nl.Inholland.model.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "balance_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    private BigDecimal amount;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public void increaseBalance(BigDecimal quant){
        BigDecimal newAmount = amount.add(quant);
        setAmount(newAmount);
    }

    public void decreaseBalance(BigDecimal quant){
        if(this.amount.compareTo(quant) == -1){
            throw new IllegalArgumentException("Not sufficients funds");
        }
        BigDecimal newAmount = amount.subtract(quant);
        setAmount(newAmount);
    }

}
