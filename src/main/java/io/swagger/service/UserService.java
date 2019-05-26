package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.User;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private boolean sorted;
    private int entries;
    private Date dateFrom;
    private Date dateTo;
    private List<User> users = new ArrayList<>(
            Arrays.asList(
                new User(5L,"Adolf"),
                new User( 6L, "Peter"),
                new User(12L, "Ulf")
            ));


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


    public UserService() {

    }

    public List<User> getUsers() {
        if (sorted) {
            users = users.stream().sorted().collect(Collectors.toList());
        }
        if (entries > 0) {
            users = new ArrayList<>(users.subList(0, entries));
        }
        return users;
    }
    public User getUser(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        throw new NoSuchElementException();
    }

}
