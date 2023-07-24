package souzxvini.com.ToDoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import souzxvini.com.ToDoAPI.dto.ActivityDayResponse;
import souzxvini.com.ToDoAPI.dto.ActivityListResponse;
import souzxvini.com.ToDoAPI.service.ActivityService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/activity")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ActivityListResponse getActivity(Principal principal) throws Exception {
        return activityService.getActivity(principal);
    }

}
