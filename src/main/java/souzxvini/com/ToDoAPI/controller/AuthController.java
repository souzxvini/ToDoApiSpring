package souzxvini.com.ToDoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.dto.*;
import souzxvini.com.ToDoAPI.service.AuthService;
import souzxvini.com.ToDoAPI.service.EmailService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @PostMapping
        public JwtResponse createAuthenticationToken(@RequestBody LoginForm form) throws Exception {
        return authService.createAuthenticationToken(form);
    }
    @PostMapping(value = "confirmAuthenticatedUserData")
    public boolean confirmAuthenticatedUserData(@RequestBody LoginForm loginForm, Principal principal) throws Exception {
        return authService.confirmAuthenticatedUserData(loginForm, principal);
    }

    @GetMapping(value= "sendEmailCode")
    public void confirmAuthenticatedUserData(@RequestParam String email) throws Exception {
        authService.sendEmailToUser(email);
    }
}
