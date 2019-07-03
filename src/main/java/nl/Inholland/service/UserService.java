package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.Employee;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.UserRequest;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.security.JwtTokenProvider;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService extends AbstractService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    public void registerUser(UserRequest userRequest) {
        User newUser;
        if(userRequest.getInitRole().equals("ROLE_EMPLOYEE")){
            newUser = new Employee();
        }else if (userRequest.getInitRole().equals("ROLE_CUSTOMER")){
            newUser = new Customer();
        }else{
            throw new NoSuchElementException();
        }
        if(userRequest != null){
            if(!userRequest.getFirstName().equals(null) || !userRequest.getFirstName().isEmpty()) newUser.setFirstName(userRequest.getFirstName());
            if(!userRequest.getLastName().equals(null) || !userRequest.getLastName().isEmpty()) newUser.setLastName(userRequest.getLastName());
            if(!userRequest.getEmail().equals(null) || !userRequest.getEmail().isEmpty()) newUser.setEmail(userRequest.getEmail());
            if(!userRequest.getPhone().equals(null) || !userRequest.getPhone().isEmpty()) newUser.setPhone(userRequest.getPhone());
            if(!userRequest.getUsername().equals(null) || !userRequest.getUsername().isEmpty()) newUser.setUsername(userRequest.getUsername());
            if(!userRequest.getPassword().equals(null) || !userRequest.getPassword().isEmpty()) newUser.setPassword(userRequest.getPassword());
            if(!userRequest.getDateCreated().equals(null) || !userRequest.getDateCreated().isEmpty()) newUser.setDateCreated(userRequest.getDateCreated());
            if(!userRequest.getBirthday().equals(null) || !userRequest.getBirthday().isEmpty()) newUser.setBirthday(userRequest.getBirthday());
        }else{
            throw new NullPointerException();
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
    }

    public List<User> getUsers(String search) {
        Specification<User> spec = getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));
        return userRepo.findAll(spec);
    }

    public User getUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new NoSuchElementException();
        }
    }

    public String auth(String username, String rawPassword) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username " + username + "not found"));
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return jwtTokenProvider.createToken(username, user.getRoles());
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    /*
    public void attachAccount(long userId, long accountId) {
        userRepo.save(
                userRepo.getOne(userId)
                        .addAccountsItem(
                                accoRepo.getOne(accountId)));
    }

    public void editUser(User user){
        userRepo.save(user);
    }*/
}
