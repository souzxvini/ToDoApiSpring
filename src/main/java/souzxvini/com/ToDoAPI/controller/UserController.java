package souzxvini.com.ToDoAPI.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import souzxvini.com.ToDoAPI.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) throws Exception {

       userService.addUser(userRequest);

       return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @GetMapping(value = "/{id}")
    public UserResponse getUserDetails(@PathVariable Long id) throws Exception {
        return userService.getUserDetails(id);
   }


   @PutMapping(value = "updateUserData/{id}")
   public UserResponse updtateUserData(@PathVariable Long id, @RequestBody UserChangeDataRequest userChangeDataRequest) throws Exception {
       return userService.updateUserData(id, userChangeDataRequest);
   }

    @PutMapping(value = "updateUserPassword")
    public ResponseEntity updtateUserPassword(@RequestParam String email, @RequestBody UserChangePasswordRequest changePasswordRequest) throws Exception {
        return userService.updateUserPassword(email, changePasswordRequest);
    }







}
