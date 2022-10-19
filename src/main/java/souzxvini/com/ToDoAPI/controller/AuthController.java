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

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping
        public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginForm form) throws Exception {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        final String token = jwtUtil.gerarToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
