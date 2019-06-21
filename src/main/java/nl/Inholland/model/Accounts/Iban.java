package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String ibanCode;

    @OneToOne(mappedBy = "iban")
    private Account account;

    @ManyToOne
    @JoinColumn
    private User user;

    private CountryCodeEnum countryCode;

    private String checkDigits;

    public BankCodeEnum bank = BankCodeEnum.INHO;

    private String bban;

    @Override
    public String toString() {
        return this.countryCode + "" + this.checkDigits + this.bank + this.bban;
    }
}
