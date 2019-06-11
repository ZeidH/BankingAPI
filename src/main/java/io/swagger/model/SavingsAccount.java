package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Account;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SavingsAccount
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@Entity
public class SavingsAccount extends Account  {
  @JsonProperty("interestRate")
  private BigDecimal interestRate = null;

  public SavingsAccount interestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
    return this;
  }

  /**
   * Get interestRate
   * @return interestRate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SavingsAccount savingsAccount = (SavingsAccount) o;
    return Objects.equals(this.interestRate, savingsAccount.interestRate) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interestRate, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavingsAccount {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    interestRate: ").append(toIndentedString(interestRate)).append("\n");
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
