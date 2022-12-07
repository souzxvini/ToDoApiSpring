package souzxvini.com.ToDoAPI.service;

import org.springframework.data.domain.Pageable;
import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.model.Status;
import souzxvini.com.ToDoAPI.model.Task;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.TaskRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> showTasks(Principal principal) {

        String userName = principal.getName();

        List<Task> tasks = taskRepository.findByUserEmail(userName);

        List<TaskResponse> response = new ArrayList<TaskResponse>();

        tasks.stream().forEach(game -> {
            response.add(TaskResponse.builder()
                    .id(game.getId())
                    .description(game.getDescription())
                    .status(game.getStatus())
                    .build());
        });
        return response;
    }

    public List<TaskResponse> showToDoTasks(Principal principal, Pageable pageable) {

        String userName = principal.getName();

        List<Task> tasks = taskRepository.findByUserEmail(userName, pageable);

        List<TaskResponse> response = new ArrayList<TaskResponse>();

        tasks.stream().forEach(game -> {
            if(game.getStatus().getName() == "TO DO"){
                response.add(TaskResponse.builder()
                        .id(game.getId())
                        .description(game.getDescription())
                        .status(game.getStatus())
                        .build());
            }
        });
        return response;
    }
    public List<TaskResponse> showDoneTasks(Principal principal, Pageable pageable) {

        String userName = principal.getName();

        List<Task> tasks = taskRepository.findByUserEmail(userName, pageable);

        List<TaskResponse> response = new ArrayList<TaskResponse>();

        tasks.stream().forEach(game -> {
            if(game.getStatus().getName() == "DONE"){
                response.add(TaskResponse.builder()
                        .id(game.getId())
                        .description(game.getDescription())
                        .status(game.getStatus())
                        .build());
            }
        });
        return response;
    }


    public TaskResponse addTask(TaskRequest taskRequest, Principal principal) throws Exception {

        String username = principal.getName();

        Optional<User> optional = userRepository.findByEmail(username);

        if(!(optional.isEmpty())){
            User user = optional.get();

            Task task = Task.builder()
                    .description(taskRequest.getDescription())
                    .status(Status.TO_DO)
                    .user(user)
                    .build();

            taskRepository.save(task);
            return TaskResponse.builder()
                    .id(task.getId())
                    .description(taskRequest.getDescription())
                    .status(Status.TO_DO)
                    .build();
        }
        throw new UserPrincipalNotFoundException("User not found");
    }

    public TaskResponse taskDetails(Long id) throws Exception {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            return TaskResponse.builder()
                    .id(task.get().getId())
                    .description(task.get().getDescription())
                    .status(task.get().getStatus())
                    .build();
        }else {
            throw new Exception("This game doesn't exists.");
        }
    }

    public void deleteTask(Long id) throws Exception {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            taskRepository.deleteById(id);
        }else{
            throw new Exception("Esse game não existe");
        }
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) throws Exception {
        Optional<Task> optional = taskRepository.findById(id);

        if(!(optional.isEmpty())){
            taskRepository.save(Task.builder()
                    .id(id)
                    .description(taskRequest.getDescription())
                    .status(optional.get().getStatus())
                    .user(optional.get().getUser())
                    .build());

            return TaskResponse.builder()
                    .id(id)
                    .description(taskRequest.getDescription())
                    .status(optional.get().getStatus())
                    .build();
        }else{
            throw new Exception("This task doesn't exists.");
        }
    }

    public boolean isTaskStatusDone(Long id) throws Exception{
        Optional<Task> optional = taskRepository.findById(id);
        if(!(optional.isEmpty())){
            Task task = optional.get();
            if(task.getStatus().toString().equals("TO_DO") ||task.getStatus().toString() == "TO_DO" || task.getStatus() == Status.TO_DO ){
                task.setStatus(Status.DONE);
                taskRepository.save(task);
                return true;
            }
            if(task.getStatus().toString().equals("DONE") ||task.getStatus().toString() == "DONE" ) {
                task.setStatus(Status.TO_DO);
                taskRepository.save(task);
                return false;
            }
            return false;
        } else {
            throw new Exception("This task doesn't exists.");
        }
    }


}
