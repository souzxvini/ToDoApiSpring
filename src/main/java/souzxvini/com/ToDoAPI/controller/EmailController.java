package souzxvini.com.ToDoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.dto.JwtResponse;
import souzxvini.com.ToDoAPI.dto.LoginForm;
import souzxvini.com.ToDoAPI.service.AuthService;
import souzxvini.com.ToDoAPI.service.EmailService;

import java.security.Principal;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private AuthService authService;
    @Autowired
    private EmailService emailService;

    @GetMapping
    public void confirmAuthenticatedUserData(@RequestParam String email) throws Exception {
        emailService.sendEmailToUser(email);
    }


}
