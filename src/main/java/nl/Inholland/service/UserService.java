package nl.Inholland.service;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.model.Users.User;
import nl.Inholland.repository.AccountRepository;
import nl.Inholland.repository.IbanRepository;
import nl.Inholland.repository.TransactionRepository;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService extends AbstractService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo,JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public void deleteUser(Long id) {
        userRepo.getOne(id).setNotLocked(false);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getUsers(String search) {
        Specification<User> spec = getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));
        return userRepo.findAll(spec);
    }

    public User getUser(Long id) {
        User user = userRepo.getOne(id);
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

    public void attachAccount(long userId, long accountId) {
        userRepo.save(
                userRepo.getOne(userId)
                        .addAccountsItem(
                                accoRepo.getOne(accountId)));
    }


}
