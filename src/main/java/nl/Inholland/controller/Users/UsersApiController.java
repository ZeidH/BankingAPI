package nl.Inholland.controller.Users;

import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.UserRequest;
import nl.Inholland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UsersApiController {

    private final UserService service;

    @Autowired
    public UsersApiController(UserService service) {
        this.service = service;
    }
//      Goes through PUT instead > No way to disable a user
//    @RequestMapping(value = "/Employee/Users/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id") Long id) {
//        service.disableUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @RequestMapping(value = "/Employee/Users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUser( @PathVariable("id") Long id){
        return new ResponseEntity<>(service.getUser(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "search", required = false) String search){
        return new ResponseEntity<>(service.getUsers(search),HttpStatus.OK);
    }


    @RequestMapping(value = "/Employee/Users", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> registerUser(@RequestBody UserRequest userRequest) {
        try{
            service.registerUser(userRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(NullPointerException | NoSuchElementException elementExp){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(value = "/Employee/Users", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity<Void> editUser(@RequestBody User user){
//        service.editUser(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    }


//  //  public ResponseEntity<Void> validateRequest(){
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
}
