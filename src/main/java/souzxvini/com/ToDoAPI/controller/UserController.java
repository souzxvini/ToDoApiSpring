package souzxvini.com.ToDoAPI.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import souzxvini.com.ToDoAPI.dto.UserRequest;
import souzxvini.com.ToDoAPI.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.service.UserService;

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

}
