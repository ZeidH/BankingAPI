package io.swagger.model;

import java.util.Collection;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import static java.util.stream.Collectors.toList;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@Entity
@Data
public class User implements UserDetails {
  //region Declarations & Constructors
  @Id
  @SequenceGenerator(name = "userId_seq", initialValue = 10000001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_seq")
  @JsonProperty("id")
  private Long id;

  @JsonProperty("first_name")
  private String firstName = null;

  @JsonProperty("last_name")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("phone")
  private String phone = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("dateCreated")
  private String dateCreated = null;

  @JsonProperty("birthday")
  private String birthday = null;

  @JsonProperty("accounts")
  @Valid
  @OneToMany
  private List<Account> accounts = new ArrayList<Account>();

public User(){}
  public User(String firstName, String lastName, String email, String phone, String username, String password, String dateCreated, String birthday, List<Account> accounts) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.username = username;
    this.password = password;
    this.dateCreated = dateCreated;
    this.birthday = birthday;
    roles.add("ROLE_EMPLOYEE");
    this.accounts = accounts;
  }
  //endregion

  //region Generated Annotations and Getters/Setters
  /**
   * Get id
   * @return id
   **/

  @ApiModelProperty(example = "124254", required = true, value = "")
  @NotNull

  public Long getId() {
    return id;
  }

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(example = "John", required = true, value = "")
  @NotNull

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(example = "Doe", required = true, value = "")
  @NotNull

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(example = "test@bank.com", required = true, value = "")
  @NotNull

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  **/
  @ApiModelProperty(example = "+31631231234", required = true, value = "")
  @NotNull

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Get username
   * @return username
  **/
  @ApiModelProperty(example = "John-Doe", required = true, value = "")
  @NotNull
  public User username(String username) {
    this.username = username;
    return this;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getUsername() {
    return username;
  }

  /**
   * Get password
   * @return password
   **/
  @ApiModelProperty(example = "welkom21", required = true, value = "")
  @NotNull

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public User password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get dateCreated
   * @return dateCreated
   **/
  @ApiModelProperty(example = "12-05-2019 22:24:10", required = true, value = "")
  @NotNull

  public User dateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
    return this;
  }

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public User birthday(String birthday) {
    this.birthday = birthday;
    return this;
  }

  /**
   * Get birthday
   * @return birthday
   **/
  @ApiModelProperty(example = "27-11-1998", required = true, value = "")
  @NotNull

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public User accounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }

  public User addAccountsItem(Account accountsItem) {
    this.accounts.add(accountsItem);
    return this;
  }

  /**
   * Array of account id's(IBAN)
   * @return accounts
   **/
  @ApiModelProperty(required = true, value = "Array of account id's(IBAN)")
  @NotNull

  public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.firstName, user.firstName) &&
            Objects.equals(this.lastName, user.lastName) &&
            Objects.equals(this.email, user.email) &&
            Objects.equals(this.phone, user.phone) &&
            Objects.equals(this.username, user.username) &&
            Objects.equals(this.password, user.password) &&
            Objects.equals(this.dateCreated, user.dateCreated) &&
            Objects.equals(this.birthday, user.birthday) &&
            Objects.equals(this.accounts, user.accounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, email, phone, username, password, dateCreated, birthday, accounts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");

    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
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
  //endregion

  // Make false
  //region Implemented UserDetails
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  //endregion



  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
  }

  public List<String> getRoles() {
    return roles;
  }
}
