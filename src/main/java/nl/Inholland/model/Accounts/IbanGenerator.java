package nl.Inholland.model.Accounts;

import nl.Inholland.enumerations.BankCodeEnum;
import nl.Inholland.enumerations.CountryCodeEnum;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class IbanGenerator {

    private static String generateBban(){

        //------- 10 random ending digits -------//
        int length = 10;
        String digits = "0123456789";
        Random rnd = new Random();

        //------- Append the digits to a String randomly -------//
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(digits);
        appendChar.accept(digits);
        while (result.size() < length)
            appendChar.accept(digits);
        Collections.shuffle(result, rnd);
        String randomBban = String.join("", result);

        return randomBban;
    }


    public static Iban makeIban(CountryCodeEnum country, BankCodeEnum bank){
        Iban iban = new Iban();
        iban.setCountryCode(country);
        iban.setBank(bank);
        iban.setBban(generateBban());
        String checkDigits = generateCheckDigits(iban);
        iban.setCheckDigits(checkDigits);

        iban.buildIbanCode();

        return iban;
    }

    public static Iban makeIban(CountryCodeEnum country, BankCodeEnum bank, String bban){
        Iban iban = new Iban();
        iban.setCountryCode(country);
        iban.setBank(bank);
        iban.setBban(bban);
        String checkDigits = generateCheckDigits(iban);
        iban.setCheckDigits(checkDigits);

        iban.buildIbanCode();

        return iban;
    }

    //---------Turn every char to a numeric value, so A=10, B=11, C=13...-------//

    private static String codeToNumberBased(String code){
        String baseCode = "";

        for (char ch: code.toCharArray()) {
            baseCode += Character.getNumericValue(ch);
        }

        return baseCode;
    }

    //---------Generate check digits based on the official algorithm -------//

    private static String generateCheckDigits(Iban iban){ //from https://www.ibantest.com/en/how-is-the-iban-check-digit-calculated

        //Enums to String
        String countryCode = iban.getCountryCode().toString();
        String bank = iban.getBank().toString();

        //Every text part of the account number is turned into numbers, the 00 at the end represents the check digits
        String arrangedCode = codeToNumberBased(bank) + "" + iban.getBban() + "" + codeToNumberBased(countryCode) + "00";

        //We perform a remainder operation and we format the result into two digits
        BigDecimal arrangedCodeNum = new BigDecimal(arrangedCode);
        BigDecimal digits = new BigDecimal(98).subtract(arrangedCodeNum.remainder(new BigDecimal(97)));
        DecimalFormat df = new DecimalFormat("00");
        String checkDigits = df.format(digits);

        return checkDigits;
    }

}
