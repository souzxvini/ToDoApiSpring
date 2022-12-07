package souzxvini.com.ToDoAPI.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.service.TaskService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskservice;

   @GetMapping
   public List<TaskResponse> showTasks(Principal principal){
       return taskservice.showTasks(principal);
   }

    @GetMapping(value = "/todo")
    public List<TaskResponse> showToDoTasks(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable,
                                            Principal principal){
        return taskservice.showToDoTasks(principal, pageable);
    }

    @GetMapping(value = "/done")
    public List<TaskResponse> showDoneTasks(@PageableDefault(sort = "id",direction = Sort.Direction.DESC,size = Integer.MAX_VALUE) Pageable pageable,
                                            Principal principal){
        return taskservice.showDoneTasks(principal, pageable);
    }

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskResponse addTask(@RequestBody @Valid TaskRequest taskRequest, Principal principal) throws Exception{
       return taskservice.addTask(taskRequest, principal);
   }

   @DeleteMapping(value="/{id}")
   public void deleteTask(@PathVariable Long id) throws Exception {
       taskservice.deleteTask(id);
   }

   @GetMapping(value = "/{id}")
    public TaskResponse taskDetails(@PathVariable Long id) throws Exception {
       return taskservice.taskDetails(id);
   }

   @PutMapping(value = "/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) throws Exception {
       return taskservice.updateTask(id, taskRequest);
   }

    @GetMapping(value = "isTaskStatusDone/{id}")
    public boolean isTaskStatusDone(@PathVariable Long id) throws Exception {
        return taskservice.isTaskStatusDone(id);
    }
}
