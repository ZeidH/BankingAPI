package nl.Inholland.controller.Users;

import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.Employee;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.UserRequest;
import nl.Inholland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersApiController {

    private final UserService service;

    @Autowired
    public UsersApiController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/Employee/Users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id", required = true) Long id) {
        service.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/Employee/Users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUser( @PathVariable("id") Long id){
        return new ResponseEntity<User>(service.getUser(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "search", required = false) String search){
        return new ResponseEntity<List<User>>(service.getUsers(search),HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Users", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> registerUser(@RequestBody(required = true) UserRequest userRequest) {
        User newUser;
        if(userRequest.getInitRole() == "ROLE_EMPLOYEE"){
            newUser = new Employee();
        }else if (userRequest.getInitRole() == "ROLE_CUSTOMER"){
            newUser = new Customer();
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            //newUser.name("Placeholder").balance(new BigDecimal(0));
        }
        service.registerUser(newUser);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<Map<Object,Object>> authenticate(@RequestParam Map<String,String> param) {
        try {
            String token = service.auth(param.get("username"), param.get("password"));
            Map<Object, Object> key = new HashMap<>();
            key.put("username", param.get("username").toString());
            key.put("token", token);
            return new ResponseEntity<Map<Object,Object>>(key, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }

    }

    @RequestMapping(value = "/Employee/Users", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> attachAccountToUser(@RequestParam(value = "userId", required = true) long userId, @RequestParam(value = "accountId", required = true) long accountId){
      //  service.attachAccount(userId, accountId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//  //  public ResponseEntity<Void> validateRequest(){
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
}
