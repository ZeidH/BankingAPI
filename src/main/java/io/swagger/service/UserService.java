package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repo;
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

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
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

}
