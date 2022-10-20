package souzxvini.com.ToDoAPI.service;

import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.JwtResponse;
import souzxvini.com.ToDoAPI.dto.LoginForm;
import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.repository.TaskRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;
import souzxvini.com.ToDoAPI.security.JwtUtil;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponse createAuthenticationToken(LoginForm form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        final String token = jwtUtil.gerarToken(authentication);

        return new JwtResponse(token, form.getEmail());
    }

}
