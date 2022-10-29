package souzxvini.com.ToDoAPI.service;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import souzxvini.com.ToDoAPI.dto.*;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.model.User;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.security.Principal;
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
    public UserResponse updtateUserData(Long id, UserChangeDataRequest userChangeDataRequest) throws Exception {
        Optional<User> optional = userRepository.findById(id);

        if(!(optional.isEmpty())){

            User user = User.builder()
                    .id(id)
                    .email(userChangeDataRequest.getEmail())
                    .name(userChangeDataRequest.getName())
                    .password(optional.get().getPassword())
                    .roles(optional.get().getRoles())
                    .build();
            System.out.println("ewcqwerv" + user);
            if(user.getRoles().isEmpty()){
                throw new Exception("You don't have access to do this.");
            } else{
                user.getRoles().removeAll(user.getRoles());
                System.out.println("c3ervgb4rtbn5tyn5tynt");
            }
            System.out.println(user);
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

}
