package souzxvini.com.ToDoAPI.controller;

import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.dto.*;
import souzxvini.com.ToDoAPI.security.JwtUtil;
import souzxvini.com.ToDoAPI.security.UserDetailsServiceImpl;
import souzxvini.com.ToDoAPI.service.AuthService;
import souzxvini.com.ToDoAPI.service.EmailService;
import souzxvini.com.ToDoAPI.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @PostMapping
        public JwtResponse createAuthenticationToken(@RequestBody LoginForm form) throws Exception {
        return authService.createAuthenticationToken(form);
    }
    @GetMapping(value = "confirmAuthenticatedUserData")
    public boolean confirmAuthenticatedUserData(@RequestBody LoginForm loginForm, Principal principal) throws Exception {
        return authService.confirmAuthenticatedUserData(loginForm, principal);
    }

    @GetMapping(value= "sendEmailCode")
    public void confirmAuthenticatedUserData(@RequestParam String email) throws Exception {
        authService.sendEmailToUser(email);
    }
}
