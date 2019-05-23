package io.swagger.service;

import io.swagger.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

@Service
public class UserService {

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
    List<User> users = new ArrayList<>(
            Arrays.asList(
                    new User(5L,"Adolf"),
                    new User( 6L, "Peter"),
                    new User(12L, "Ulf")
            )
    );
    public List<User> getUsers(){
        if(sorted){

        }
        if(entries == 0){

        }

        return users;
    }

    public ResponseEntity<User> getUser(int id){
        for(User user : users){
            if(user.getId() == id){
                return ResponseEntity.ok(user);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
