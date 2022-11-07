package souzxvini.com.ToDoAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import souzxvini.com.ToDoAPI.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.service.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "updateLoggedUserPassword")
    public ResponseEntity updtateUserPassword(@RequestParam String email, @RequestBody UserChangePasswordRequest changePasswordRequest) throws Exception {
        return userService.updateUserPassword(email, changePasswordRequest);
    }

    @PutMapping(value = "/updateForgotPassword")
    public ResponseEntity updateForgotPassword( @RequestBody ChangeForgotPasswordRequest changeForgotPasswordRequest) throws Exception {
        return userService.updateForgotPassword( changeForgotPasswordRequest);
    }

    @PutMapping(value = "/clearUserRandomCodeAndRole")
    public ResponseEntity clearUserRandomCodeAndRole() throws Exception {
        return userService.clearUserRandomCodeAndRole();
    }

    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean verifyIfUserExists(@RequestParam String email){

        return userService.verifyIfUserExists(email);
    }

}
