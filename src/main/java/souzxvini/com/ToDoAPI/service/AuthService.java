package souzxvini.com.ToDoAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import souzxvini.com.ToDoAPI.dto.CodeRequest;
import souzxvini.com.ToDoAPI.dto.CodeResponse;
import souzxvini.com.ToDoAPI.dto.JwtResponse;
import souzxvini.com.ToDoAPI.dto.LoginForm;
import souzxvini.com.ToDoAPI.model.EmailMessages;
import souzxvini.com.ToDoAPI.model.Role;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.RoleRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;
import souzxvini.com.ToDoAPI.security.JwtUtil;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

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

    @Autowired
    private EmailService emailService;

    public JwtResponse createAuthenticationToken(LoginForm form) throws Exception {

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        Optional<User> optional = userRepository.findByEmail(form.getEmail());

        if(!optional.isEmpty()){
            User user = optional.get();

            final String token = jwtUtil.gerarToken(authentication);

            return new JwtResponse(token, form.getEmail(), user.getName());
        }else{
            throw new Exception("User doesn't exists");
        }


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

    public void sendEmailToUser(String email) throws Exception {

        Optional<User> optional = userRepository.findByEmail(email);

        if(!(optional.isEmpty())){
            User user = optional.get();

                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                String randomCode =  String.format("%06d", number);

                user.setRandomCode(randomCode);
                userRepository.save(user);

                this.emailService.sendEmail(email, EmailMessages.createTittle(user), EmailMessages.messageToUser(user, randomCode));
        } else{
            throw new Exception("There's no user registered with this email.");
        }
    }
}
