package io.swagger.model;

import ch.qos.logback.classic.db.names.ColumnName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Iban
 */
@Entity
@Validated
@Table(name = "iban")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-09T19:14:52.572Z[GMT]")
public class Iban {

  public Iban() {
  }

  @Id
  @JsonProperty("ibanCode")
  @Column(name = "ibanCode")
  private String ibanCode;

  @OneToOne(mappedBy = "iban")
  private Account account = null;


  public final String BANK = "INHO";

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

  /**
   * Gets or Sets countryCode
   */

  public enum CountryCodeEnum {
    NL("NL");

    private String value;

    CountryCodeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CountryCodeEnum fromValue(String text) {
      for (CountryCodeEnum b : CountryCodeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("countryCode")
  private CountryCodeEnum countryCode = null;

  @JsonProperty("checkDigits")
  private String checkDigits = null;

  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
  @JsonProperty("bban")
  private String bban = null;

  public Iban countryCode(CountryCodeEnum countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Get countryCode
   * @return countryCode
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public CountryCodeEnum getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(CountryCodeEnum countryCode) {
    this.countryCode = countryCode;
  }

  public Iban checkDigits(String checkDigits) {
    this.checkDigits = checkDigits;
    return this;
  }

  /**
   * Get checkDigits
   * @return checkDigits
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public String getCheckDigits() {
    return checkDigits;
  }

  private static String codeToNumberBased(String code){
    String baseCode = "";
    for (char ch: code.toCharArray()) {

      baseCode += Character.getNumericValue(ch);
    }
    return baseCode;
  }

  private void generateCheckDigits(){ //from https://www.ibantest.com/en/how-is-the-iban-check-digit-calculated
    String arrangedCode = "" + codeToNumberBased(this.BANK) + "" + this.bban + "" + codeToNumberBased(this.countryCode.toString()) + "00";

    BigDecimal arrangedCodeNum = new BigDecimal(arrangedCode);
    BigDecimal checkDigits = new BigDecimal(98).subtract(arrangedCodeNum.remainder(new BigDecimal(97)));
    DecimalFormat df = new DecimalFormat("00");
    this.checkDigits = df.format(checkDigits);

  }

  public String getIbanCode(){
    String IbanNumber = "" + this.getCountryCode() + "" + this.getCheckDigits() + this.BANK + this.getBban();
    return IbanNumber;
  }


  public Iban bban(String bban) {
    this.bban = bban;
    return this;
  }

  /**
   * Get bban
   * @return bban
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public String getBban() {
    return bban;
  }

  public void setBban(String bban) {
    this.bban = bban;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Iban iban = (Iban) o;
    return Objects.equals(this.countryCode, iban.countryCode) &&
            Objects.equals(this.checkDigits, iban.checkDigits) &&
            Objects.equals(this.bban, iban.bban);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryCode, checkDigits, bban);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Iban {\n");

    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    checkDigits: ").append(toIndentedString(checkDigits)).append("\n");
    sb.append("    bban: ").append(toIndentedString(bban)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
