package souzxvini.com.ToDoAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.ChangeForgotPasswordRequest;
import souzxvini.com.ToDoAPI.dto.LanguageRequest;
import souzxvini.com.ToDoAPI.dto.ThemeRequest;
import souzxvini.com.ToDoAPI.dto.UserChangeDataRequest;
import souzxvini.com.ToDoAPI.dto.UserChangePasswordRequest;
import souzxvini.com.ToDoAPI.dto.UserRequest;
import souzxvini.com.ToDoAPI.dto.UserResponse;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (userMayExists.isPresent() || !userMayExists.isEmpty()) {
            throw new Exception("Usuário ja existente!");
        } else {
            if (userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
                String regex_pattern =
                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,24}$";

                Pattern pattern = Pattern.compile(regex_pattern);
                Matcher matcher = pattern.matcher(userRequest.getPassword());

                if (matcher.matches()) {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    String unencryptedPassword = userRequest.getPassword();
                    String encodedPassword = encoder.encode(unencryptedPassword);
                    userRepository.save(User.builder()
                            .email(userRequest.getEmail())
                            .password(encodedPassword)
                            .name(userRequest.getName())
                            .language("pt")
                                    .theme("LIGHT")
                            .build());
                } else {
                    throw new Exception("Password does NOT have all the requirements");
                }
            } else {
                throw new Exception("Passwords do NOT matches");
            }
        }
    }

    public UserResponse getUserDetails(Integer id) throws Exception {
        Optional<User> optional = userRepository.findById(id);

        if (!(optional.isEmpty())) {
            return UserResponse.builder()
                    .id(id)
                    .email(optional.get().getEmail())
                    .password(optional.get().getPassword())
                    .name(optional.get().getName())
                    .build();
        }
        throw new Exception("User not found.");
    }

    public UserResponse updateUserData(String email, UserChangeDataRequest userChangeDataRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(email);

        if (!(optional.isEmpty())) {

            User user = User.builder()
                    .id(optional.get().getId())
                    .email(userChangeDataRequest.getEmail())
                    .name(userChangeDataRequest.getName())
                    .password(optional.get().getPassword())
                    .roles(optional.get().getRoles())
                    .build();

            if (user.getRoles().isEmpty()) {
                throw new Exception("You don't have access to do this.");
            } else {
                user.getRoles().removeAll(user.getRoles());
            }
            userRepository.save(user);

            return UserResponse.builder()
                    .id(optional.get().getId())
                    .email(userChangeDataRequest.getEmail())
                    .name(userChangeDataRequest.getName())
                    .build();
        } else {
            throw new Exception("This user doesn't exists.");
        }
    }

    public ResponseEntity updateUserPassword(String email, UserChangePasswordRequest userChangePasswordRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(email);

        if (!(optional.isEmpty())) {
            User user = optional.get();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String unencryptedPassword = userChangePasswordRequest.getPassword();
            String encodedPassword = encoder.encode(unencryptedPassword);

            user.setPassword(encodedPassword);

            if (!userChangePasswordRequest.getPassword().isEmpty()) {
                if (userChangePasswordRequest.getPassword().equals(userChangePasswordRequest.getConfirmPassword())) {
                    String regex_pattern =
                            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,24}$";

                    Pattern pattern = Pattern.compile(regex_pattern);
                    Matcher matcher = pattern.matcher(userChangePasswordRequest.getPassword());
                    if (matcher.matches()) {
                        if (user.getRoles().isEmpty()) {
                            throw new Exception("You don't have access to do this.");
                        } else {
                            user.getRoles().removeAll(user.getRoles());
                        }
                        userRepository.save(user);

                        return ResponseEntity.ok().build();
                    } else {
                        throw new Exception("Password does NOT have all the requirements");
                    }

                } else {
                    throw new Exception("The passwords doesn't matches.");
                }
            } else {
                throw new Exception("The password can't be null");
            }
        } else {
            throw new Exception("This user doesn't exists.");
        }
    }

    public ResponseEntity updateForgotPassword(ChangeForgotPasswordRequest changeForgotPasswordRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(changeForgotPasswordRequest.getEmail());

        if (!(optional.isEmpty())) {
            User user = optional.get();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String unencryptedPassword = changeForgotPasswordRequest.getPassword();
            String encodedPassword = encoder.encode(unencryptedPassword);
            user.setPassword(encodedPassword);

            if (!changeForgotPasswordRequest.getPassword().isEmpty()) {
                if (changeForgotPasswordRequest.getPassword().equals(changeForgotPasswordRequest.getConfirmPassword())) {

                    String regex_pattern =
                            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,24}$";

                    Pattern pattern = Pattern.compile(regex_pattern);
                    Matcher matcher = pattern.matcher(changeForgotPasswordRequest.getPassword());

                    if (matcher.matches()) {
                        if (changeForgotPasswordRequest.getRandomCode().equals(user.getRandomCode()) || changeForgotPasswordRequest.getRandomCode() == user.getRandomCode()) {
                            user.setRandomCode(null);
                            userRepository.save(user);

                            return ResponseEntity.ok().build();
                        } else {
                            throw new Exception("Wrong code!");
                        }
                    } else {
                        throw new Exception("Password does NOT have all the requirements");
                    }

                } else {
                    throw new Exception("Passwords do NOT matches.");
                }

            } else {
                throw new Exception("The code can't be empty!");
            }

        } else {
            throw new Exception("This user doesn't exists.");
        }
    }

    public ResponseEntity clearUserRandomCodeAndRole(String email) throws Exception {

        Optional<User> optional = userRepository.findByEmail(email);

        if (!(optional.isEmpty())) {
            User user = optional.get();
            if (!(user.getRandomCode() == null)) {
                user.setRandomCode(null);
            }

            if (!user.getRoles().isEmpty()) {
                user.getRoles().removeAll(user.getRoles());
            }
            userRepository.save(user);
        } else {
            throw new Exception("This user doesn't exists.");
        }

        return ResponseEntity.ok().build();
    }

    public boolean verifyIfUserExists(String nome) {
        Optional<User> optional = userRepository.findByEmail(nome);

        if (optional.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean userHasAuthorities(String email) throws Exception {
        Optional<User> optional = userRepository.findByEmail(email);

        if (optional.isEmpty()) {
            throw new Exception("User doesn't exists.");
        } else {
            User user = optional.get();

            if (user.getAuthorities().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void changeUserLanguage(LanguageRequest languageRequest, Principal principal) throws Exception {

        String userEmail = principal.getName();

        Optional<User> optional = userRepository.findByEmail(userEmail);

        if (optional.isEmpty()) {
            throw new Exception("User doesn't exists.");
        }

        User user = optional.get();
        user.setLanguage(languageRequest.getLanguage());
        userRepository.save(user);
    }

    public void changeUserTheme(ThemeRequest themeRequest, Principal principal) throws Exception {
        String userEmail = principal.getName();

        Optional<User> optional = userRepository.findByEmail(userEmail);

        if (optional.isEmpty()) {
            throw new Exception("User doesn't exists.");
        }

        User user = optional.get();
        user.setTheme(themeRequest.getTheme());
        userRepository.save(user);
    }
}
