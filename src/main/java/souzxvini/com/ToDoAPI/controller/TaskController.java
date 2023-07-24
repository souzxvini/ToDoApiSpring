package souzxvini.com.ToDoAPI.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import souzxvini.com.ToDoAPI.dto.ProgressResponse;
import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.dto.TasksFilterRequest;
import souzxvini.com.ToDoAPI.dto.UpdateTaskRequest;
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

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addTask(@RequestBody @Valid TaskRequest taskRequest, Principal principal) throws Exception{

       taskservice.addTask(taskRequest, principal);

       return new ResponseEntity(HttpStatus.OK);
   }

   @DeleteMapping(value="/{id}")
   public ResponseEntity deleteTask(@PathVariable Integer id) throws Exception {
       taskservice.deleteTask(id);

       return new ResponseEntity(HttpStatus.OK);
   }
    @GetMapping(value="/{id}")
    public TaskResponse getTask(@PathVariable Integer id) throws Exception {
        return taskservice.getTask(id);
    }

   @PutMapping
    public ResponseEntity updateTask(@RequestBody UpdateTaskRequest taskRequest, Principal principal) throws Exception {
       taskservice.updateTask(taskRequest, principal);

       return new ResponseEntity(HttpStatus.OK);
   }

    @PutMapping(value = "changeTaskStatus/{id}")
    public ResponseEntity changeTaskStatus(@PathVariable Integer id, Principal principal) throws Exception {
       taskservice.changeTaskStatus(id, principal);

       return new ResponseEntity(HttpStatus.OK);
    }

}
