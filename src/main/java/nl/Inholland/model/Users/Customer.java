package nl.Inholland.model.Users;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.model.Accounts.Account;

import javax.persistence.Entity;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Customer extends User {
    public Customer(String firstName, String lastName, String email, String phone, String username, String password, String dateCreated, String birthday) {
        super(firstName, lastName, email, phone, username, password, dateCreated, birthday);
    }

    public Customer(String firstName, String lastName, String email, String phone, String username, String password, String dateCreated, String birthday, List<Account> accounts) {
        super(firstName, lastName, email, phone, username, password, dateCreated, birthday, accounts);
    }
    protected void addAuthority(){
        this.roles.add("ROLE_CUSTOMER");
    }
}
