package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
public class Transaction   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("currency")
  private String currency = null;

  @JsonProperty("creator")
  private String creator = null;

  /**
   * Experimental - Default is Other
   */
  public enum CategoryEnum {
    LIVING("Living"),
    
    ENTERTAINMENT("Entertainment"),
    
    FOOD("Food"),
    
    TRANSPORT("Transport"),
    
    SAVING("Saving"),
    
    OTHER("Other");

    private String value;

    CategoryEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CategoryEnum fromValue(String text) {
      for (CategoryEnum b : CategoryEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("category")
  private CategoryEnum category = null;

  @JsonProperty("sender")
  private String sender = null;

  @JsonProperty("receiver")
  private String receiver = null;

  @JsonProperty("dateCreated")
  private String dateCreated = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PENDING("pending"),
    
    FAILED("failed"),
    
    PROCESSED("processed");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  public Transaction amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Transaction currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
  **/
  @ApiModelProperty(example = "EUR", required = true, value = "")
  @NotNull

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Transaction creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(example = "NL02INGB0154356789", required = true, value = "")
  @NotNull

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Transaction category(CategoryEnum category) {
    this.category = category;
    return this;
  }

  /**
   * Experimental - Default is Other
   * @return category
  **/
  @ApiModelProperty(required = true, value = "Experimental - Default is Other")
  @NotNull

  public CategoryEnum getCategory() {
    return category;
  }

  public void setCategory(CategoryEnum category) {
    this.category = category;
  }

  public Transaction sender(String sender) {
    this.sender = sender;
    return this;
  }

  /**
   * Get sender
   * @return sender
  **/
  @ApiModelProperty(example = "NL02INGB0154356789", required = true, value = "")
  @NotNull

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public Transaction receiver(String receiver) {
    this.receiver = receiver;
    return this;
  }

  /**
   * Get receiver
   * @return receiver
  **/
  @ApiModelProperty(example = "NL02INGB0153457789", required = true, value = "")
  @NotNull

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public Transaction dateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
    return this;
  }

  /**
   * Get dateCreated
   * @return dateCreated
  **/
  @ApiModelProperty(example = "12-05-2019 22:24:10", required = true, value = "")
  @NotNull

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Transaction status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.currency, transaction.currency) &&
        Objects.equals(this.creator, transaction.creator) &&
        Objects.equals(this.category, transaction.category) &&
        Objects.equals(this.sender, transaction.sender) &&
        Objects.equals(this.receiver, transaction.receiver) &&
        Objects.equals(this.dateCreated, transaction.dateCreated) &&
        Objects.equals(this.status, transaction.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency, creator, category, sender, receiver, dateCreated, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    receiver: ").append(toIndentedString(receiver)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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