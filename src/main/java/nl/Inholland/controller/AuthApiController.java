package nl.Inholland.controller;

import nl.Inholland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
 * Handles endpoint Login
 */

@RestController
public class AuthApiController {


    private final UserService service;

    @Autowired
    public AuthApiController(UserService service) {
        this.service = service;
    }


    @RequestMapping(value = "/Login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<Map<Object,Object>> authenticate(@RequestParam Map<String,String> param) {
        try {
            String token = service.auth(param.get("username"), param.get("password"));
            Map<Object, Object> key = new HashMap<>();
            key.put("username", param.get("username"));
            key.put("token", token);
            return new ResponseEntity<>(key, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }

    }
    @RequestMapping(value = "/Refresh", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> refresh() {
        return new ResponseEntity<>("todo", HttpStatus.OK);
    }


}
