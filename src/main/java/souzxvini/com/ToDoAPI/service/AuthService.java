package souzxvini.com.ToDoAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.JwtResponse;
import souzxvini.com.ToDoAPI.dto.LoginForm;
import souzxvini.com.ToDoAPI.model.Role;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.RoleRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;
import souzxvini.com.ToDoAPI.security.JwtUtil;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public JwtResponse createAuthenticationToken(LoginForm form) {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        final String token = jwtUtil.gerarToken(authentication);

        return new JwtResponse(token, form.getEmail());
    }

    public boolean confirmAuthenticatedUserData(LoginForm loginForm, Principal principal){
        Optional<User> optional = userRepository.findByEmail(principal.getName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!(optional.isEmpty())){
            User user = optional.get();
            if(loginForm.getEmail().equals(user.getEmail()) && encoder.matches(loginForm.getPassword(), user.getPassword())){
                Optional<Role> optionalRole = roleRepository.findByName("ROLE_EDITOR");
                Role role;
                if(!(optionalRole.isEmpty())){
                    role = optionalRole.get();
                    user.getRoles().add(role);
                    userRepository.save(user);
                    return true;
                }
            } else{
                return false;
            }
        }
        return false;
    }

}
