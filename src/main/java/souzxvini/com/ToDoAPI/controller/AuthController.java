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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import souzxvini.com.ToDoAPI.dto.JwtResponse;
import souzxvini.com.ToDoAPI.dto.LoginForm;
import souzxvini.com.ToDoAPI.dto.TokenDto;
import souzxvini.com.ToDoAPI.security.JwtUtil;
import souzxvini.com.ToDoAPI.security.UserDetailsServiceImpl;
import souzxvini.com.ToDoAPI.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
        public JwtResponse createAuthenticationToken(@RequestBody LoginForm form) throws Exception {
        return authService.createAuthenticationToken(form);
    }

}
