package souzxvini.com.ToDoAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import souzxvini.com.ToDoAPI.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.service.UserService;

import java.security.Principal;

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
    public UserResponse getUserDetails(@PathVariable Integer id) throws Exception {
        return userService.getUserDetails(id);
   }

   @PutMapping(value = "updateUserData")
   public UserResponse updtateUserData(@RequestParam String email, @RequestBody UserChangeDataRequest userChangeDataRequest) throws Exception {
       return userService.updateUserData(email, userChangeDataRequest);
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
    public ResponseEntity clearUserRandomCodeAndRole(@RequestParam String email) throws Exception {
        return userService.clearUserRandomCodeAndRole(email);
    }

    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean verifyIfUserExists(@RequestParam String email){
        return userService.verifyIfUserExists(email);
    }

    @GetMapping(value = "/userHasAuthorities", produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean userHasAuthorities(@RequestParam String email) throws Exception {
        return userService.userHasAuthorities(email);
    }

    @PutMapping(value = "/language", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity changeUserLanguage(@RequestBody LanguageRequest languageRequest, Principal principal) throws Exception {
        userService.changeUserLanguage(languageRequest, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/theme", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity changeUserTheme(@RequestBody ThemeRequest themeRequest, Principal principal) throws Exception {
        userService.changeUserTheme(themeRequest, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
