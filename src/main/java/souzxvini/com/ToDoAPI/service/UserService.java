package souzxvini.com.ToDoAPI.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import souzxvini.com.ToDoAPI.dto.UserRequest;
import souzxvini.com.ToDoAPI.dto.UserResponse;
import souzxvini.com.ToDoAPI.model.User;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
