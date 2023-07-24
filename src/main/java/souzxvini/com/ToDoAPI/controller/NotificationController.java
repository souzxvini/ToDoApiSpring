package souzxvini.com.ToDoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import souzxvini.com.ToDoAPI.dto.ActivityListResponse;
import souzxvini.com.ToDoAPI.dto.NotificationsResponse;
import souzxvini.com.ToDoAPI.service.ActivityService;
import souzxvini.com.ToDoAPI.service.NotificationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/notification")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/all")
    public List<NotificationsResponse> getAllNotifications(Principal principal) throws Exception {
        return notificationService.getAllNotifications(principal);
    }

    @PostMapping
    public ResponseEntity createNotification(Principal principal) throws Exception {

        notificationService.createNotification(principal);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping(value = "/setAllAsVisualized")
    public ResponseEntity setAllAsVisualized(Principal principal) throws Exception {

        notificationService.setAllAsVisualized(principal);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping(value = "/setNotificationAsClicked/{id}")
    public ResponseEntity setNotificationAsClicked(@PathVariable Integer id, Principal principal) throws Exception {

        notificationService.setNotificationAsClicked(id, principal);

        return new ResponseEntity(HttpStatus.OK);
    }


}
