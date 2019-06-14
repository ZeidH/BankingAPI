package io.swagger.service;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.swagger.QueryBuilder.*;
import io.swagger.QueryBuilder.Specifications.UserSpecification;
import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.IbanRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends AbstractService {


    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        super(userRepo, tranRepo, accoRepo, ibanRepo);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public void deleteUser(Long id) {
        userRepo.delete(userRepo.getOne(id));
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