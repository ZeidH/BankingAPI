package nl.Inholland.model.Accounts;

import com.sun.istack.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;
import nl.Inholland.model.Users.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Iban", uniqueConstraints = {@UniqueConstraint(columnNames = {"countryCode", "checkDigits", "bank", "bban"})})
public class Iban {

    @Id
    @Column(name = "ibanCode")
    @Setter(AccessLevel.NONE)
    private String ibanCode;

    @OneToOne
    @JoinColumn(name = "account", nullable = true)
    private Account account;


    private CountryCodeEnum countryCode;

    private String checkDigits;

    private BankCodeEnum bank = BankCodeEnum.INHO;

    private String bban;

    public void buildIbanCode(){
        this.ibanCode = this.toString();
    }

    @Override
    public String toString() {
        return this.countryCode + "" + this.checkDigits + this.bank + this.bban;
    }
}
