package nl.Inholland.model.Users;

import com.sun.istack.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.Inholland.enumerations.AccountType;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.Iban;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints =  {@UniqueConstraint(columnNames = {"username"})}, name = "Users")
public abstract class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "userId_seq", initialValue = 10000001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_seq")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String dateCreated;
    private String birthday;
    private boolean isNotLocked;


    @Nullable
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Map<AccountType,Iban> ibanList = new HashMap<>();

    public User(String firstName, String lastName, String email, String phone, String username, String password, String dateCreated, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.birthday = birthday;
        addAuthority();
    }

    public void addIban(AccountType type, Iban iban){
        ibanList.put(type, iban);
    }

    abstract void addAuthority();

    @ElementCollection(fetch = FetchType.EAGER)
    protected List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    public void additionalAuthority(String authority){
        roles.add(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isNotLocked;
    }
}
