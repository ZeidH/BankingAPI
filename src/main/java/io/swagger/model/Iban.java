package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Iban
 */
@Entity
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-09T19:14:52.572Z[GMT]")
public class Iban {

  public Iban() {
  }

  public Iban(Integer id, CountryCodeEnum countryCode, String bban) {
    this.countryCode = countryCode;
    this.bban = bban;

    generateCheckDigits();
  }

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @JsonProperty("id")
  private Integer id = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Iban id(Integer id) {
    this.id = id;
    return this;
  }

  public final String BANK = "INHO";

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
  private Double checkDigits = null;

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

  public Iban checkDigits(Double checkDigits) {
    this.checkDigits = checkDigits;
    return this;
  }

  /**
   * Get checkDigits
   * @return checkDigits
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public Double getCheckDigits() {
    return checkDigits;
  }

  private String codeToBase36(String code){

    String base36code = "";

    for (char ch: code.toCharArray()) {
      byte[] bytes = String.valueOf(ch).getBytes(StandardCharsets.UTF_8);
      String base36 = new BigInteger(1, bytes).toString(36);
      base36code += base36;
    }

    return base36code;
  }

  private void generateCheckDigits(){ //from https://www.ibantest.com/en/how-is-the-iban-check-digit-calculated
    String arrangedCode = "" + codeToBase36(this.BANK) + "" + this.bban + "" + codeToBase36(this.countryCode.toString()) + "00";

    Double arrengedCodeMod = Double.parseDouble(arrangedCode);

    this.checkDigits = 98 - (arrengedCodeMod % 97);
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
