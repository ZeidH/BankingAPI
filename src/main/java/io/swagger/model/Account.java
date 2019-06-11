package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@Entity
@SequenceGenerator(name = "account_seq", initialValue = 1, allocationSize=1)
public class Account   {
  public Account(Long id, BigDecimal balance, String name, Iban iban, List<Transaction> transactions) {
    this.id = id;
    this.balance = balance;
    this.name = name;
    this.iban = iban;
    this.transactions = transactions;
  }
  public Account(){}

  @JsonProperty("id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
  @Id
  private Long id = null;

  @Valid
  @JsonProperty("balance")
  private BigDecimal balance = null;

  @Valid
  @JsonProperty("name")
  private String name = null;

  @Valid
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "iban_account", referencedColumnName = "ibanCode")
  @JsonProperty("iban")
  private Iban iban = null;

  public Account iban(Iban iban) {
    this.iban = iban;
    return this;
  }

  /**
   * Get iban
   * @return iban
   **/
  @ApiModelProperty(value = "")

  @Valid
  public Iban getIban() {
    return iban;
  }

  public void setIban(Iban iban) {
    this.iban = iban;
  }

  @JsonProperty("transactions")
  @Valid
  @OneToMany
  private List<Transaction> transactions = new ArrayList<Transaction>();

  public Account id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  public Long getId() {
    return id;
  }


  /**
   * Get accountNumber
   * @return accountNumber
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Account balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Account name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Account transactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public Account addTransactionsItem(Transaction transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<Transaction>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Array of transactions ids
   * @return transactions
  **/
  @ApiModelProperty(value = "Array of transactions ids")

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.id, account.id) &&
        Objects.equals(this.iban, account.iban) &&
        Objects.equals(this.balance, account.balance) &&
        Objects.equals(this.name, account.name) &&
        Objects.equals(this.transactions, account.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, iban, balance, name, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountNumber: ").append(toIndentedString(iban)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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
