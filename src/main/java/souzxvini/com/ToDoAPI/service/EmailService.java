package souzxvini.com.ToDoAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import souzxvini.com.ToDoAPI.dto.UserChangeDataRequest;
import souzxvini.com.ToDoAPI.dto.UserChangePasswordRequest;
import souzxvini.com.ToDoAPI.dto.UserRequest;
import souzxvini.com.ToDoAPI.dto.UserResponse;
import souzxvini.com.ToDoAPI.model.EmailMessages;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    public EmailService( final JavaMailSender javaMailSender,UserRepository userRepository){
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendEmail(String to, String tittle, String content){
        var message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(tittle);
        message.setText(content);
        javaMailSender.send(message);
    }

    String randomCode = "";

    public void sendEmailToUser(@RequestParam String email) throws Exception {

        Optional<User> optional = userRepository.findByEmail(email);

        if(!(optional.isEmpty())){
            User user = optional.get();

            Random rnd = new Random();
            int number = rnd.nextInt(999999);

            // this will convert any number sequence into 6 character.
            randomCode =  String.format("%06d", number);

            sendEmail(email, EmailMessages.createTittle(user), EmailMessages.messageToUser(user, randomCode));
        } else{
            throw new Exception("There's no user registered with this email.");
        }


    }

}
