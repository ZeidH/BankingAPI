package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repo;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private boolean sorted;
    private int entries;
    private Date dateFrom;
    private Date dateTo;


    public void setEntries(int entries) {
        this.entries = entries;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setSorting(boolean sorted){
        this.sorted = sorted;
    }


    public UserService(UserRepository repo, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public void deleteUser(Long id){
        repo.delete(repo.findOne(id));
    }
    public void registerUser(User user){
        repo.save(user);
    }

    public List<User> getUsers() {
        List<User> users = null;
        if(dateFrom != null || dateTo != null){
            users = repo.getUsersByDate(dateFrom, dateTo);
        }else{
            users = repo.findAll();
        }

        if (sorted) {
            users = users.stream().sorted().collect(Collectors.toList());
        }
        if (entries > 0) {
            users = new ArrayList<>(users.subList(0, entries));
        }
        return users;
    }

    public User getUser(Long id){
        User user = repo.findOne(id);
        if(user != null){
            return user;
        }
        else{
            throw new NoSuchElementException();
        }
    }

    public String auth(String username, String password){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.createToken(username, repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

    }

}
