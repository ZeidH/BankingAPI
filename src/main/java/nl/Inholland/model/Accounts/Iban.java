package nl.Inholland.model.Accounts;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.CountryCodeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Data
@Entity
@NoArgsConstructor
@Table(name = "iban")
public class Iban {

    @Id
    @Column(name = "ibanCode")
    private String ibanCode;

    // Causes infinite loopie
//    @OneToOne(mappedBy = "iban")
//    private Account account;

    public final String BANK = "INHO";
    private CountryCodeEnum countryCode;
    private String checkDigits;
    private String bban;

    public Iban(String bban) {
        this.bban = bban;
    }

    public String getIbanCode(){
        String IbanNumber = "" + this.getCountryCode() + "" + this.getCheckDigits() + this.BANK + this.getBban();
        return IbanNumber;
    }

    public void buildIban(){
        this.countryCode = CountryCodeEnum.NL;
        if(bban == null){
            generateBban();
        }
        generateCheckDigits();
        this.ibanCode = getIbanCode();
    }

    private void generateBban(){
        Random random = new Random();
        int length = 10;
        String digits = "0123456789";
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(digits);
        appendChar.accept(digits);
        while (result.size() < length)
            appendChar.accept(digits);
        Collections.shuffle(result, rnd);
        String randomBban = String.join("", result);
        this.bban = randomBban;
    }

    private void generateCheckDigits(){ //from https://www.ibantest.com/en/how-is-the-iban-check-digit-calculated
        String arrangedCode = "" + codeToNumberBased(this.BANK) + "" + this.bban + "" + codeToNumberBased(this.countryCode.toString()) + "00";

        BigDecimal arrangedCodeNum = new BigDecimal(arrangedCode);
        BigDecimal checkDigits = new BigDecimal(98).subtract(arrangedCodeNum.remainder(new BigDecimal(97)));
        DecimalFormat df = new DecimalFormat("00");
        this.checkDigits = df.format(checkDigits);

    }
    private static String codeToNumberBased(String code){
        String baseCode = "";
        for (char ch: code.toCharArray()) {

            baseCode += Character.getNumericValue(ch);
        }
        return baseCode;
    }


}
