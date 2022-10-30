package souzxvini.com.ToDoAPI.service;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import souzxvini.com.ToDoAPI.dto.*;
import souzxvini.com.ToDoAPI.model.EmailMessages;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.model.User;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void addUser(UserRequest userRequest) throws Exception {

        Optional<User> userMayExists = userRepository.findByEmail(userRequest.getEmail());

        if(userMayExists.isPresent() || !userMayExists.isEmpty()) {
            throw new Exception("Usuário ja existente!");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String unencryptedPassword = userRequest.getPassword();

            String encodedPassword = encoder.encode(unencryptedPassword);
            userRepository.save(User.builder()
                    .email(userRequest.getEmail())
                    .password(encodedPassword)
                    .name(userRequest.getName())
                    .build());
        }
    }

    public UserResponse getUserDetails(Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);

        if(!(optional.isEmpty())){
            return UserResponse.builder()
                    .id(id)
                    .email(optional.get().getEmail())
                    .password(optional.get().getPassword())
                    .name(optional.get().getName())
                    .build();
        }
        throw new Exception("User not found.");
    }
    public UserResponse updateUserData(Long id, UserChangeDataRequest userChangeDataRequest) throws Exception {
        Optional<User> optional = userRepository.findById(id);

        if(!(optional.isEmpty())){

            User user = User.builder()
                    .id(id)
                    .email(userChangeDataRequest.getEmail())
                    .name(userChangeDataRequest.getName())
                    .password(optional.get().getPassword())
                    .roles(optional.get().getRoles())
                    .build();

            if(user.getRoles().isEmpty()){
                throw new Exception("You don't have access to do this.");
            } else{
                user.getRoles().removeAll(user.getRoles());
            }
            userRepository.save(user);

            return UserResponse.builder()
                    .id(id)
                    .email(userChangeDataRequest.getEmail())
                    .name(userChangeDataRequest.getName())
                    .build();
        }else{
            throw new Exception("This user doesn't exists.");
        }
    }
    public ResponseEntity updateUserPassword(String email, UserChangePasswordRequest userChangePasswordRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(email);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String unencryptedPassword = userChangePasswordRequest.getPassword();

        String encodedPassword = encoder.encode(unencryptedPassword);

        if(!(optional.isEmpty())){

            User user = User.builder()
                    .id(optional.get().getId())
                    .email(optional.get().getEmail())
                    .name(optional.get().getName())
                    .password(encodedPassword)
                    .roles(optional.get().getRoles())
                    .build();

            if(user.getRoles().isEmpty()){
                throw new Exception("You don't have access to do this.");
            } else{
                user.getRoles().removeAll(user.getRoles());
            }
            userRepository.save(user);

            return ResponseEntity.ok().build();
        }else{
            throw new Exception("This user doesn't exists.");
        }
    }

    public ResponseEntity updateForgotPassword( ChangeForgotPasswordRequest changeForgotPasswordRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(changeForgotPasswordRequest.getEmail());

        if(!(optional.isEmpty())){
            User user = optional.get();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String unencryptedPassword = changeForgotPasswordRequest.getPassword();

            String encodedPassword = encoder.encode(unencryptedPassword);

            user.setPassword(encodedPassword);

            if(changeForgotPasswordRequest.getRandomCode().equals(user.getRandomCode()) || changeForgotPasswordRequest.getRandomCode() == user.getRandomCode()){
                user.setRandomCode(null);

                userRepository.save(user);

                return ResponseEntity.ok().build();
            } else{
                throw new Exception("Wrong code!");
            }
        }else{
            throw new Exception("This user doesn't exists.");
        }
    }


}
