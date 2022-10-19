package souzxvini.com.ToDoAPI.service;

import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.model.Task;
import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.repository.TaskRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;

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

        List<Task> tasks = taskRepository.findByUserName(userName);

        List<TaskResponse> response = new ArrayList<TaskResponse>();

        tasks.stream().forEach(game -> {
            response.add(TaskResponse.builder()
                    .id(game.getId())
                    .descricao(game.getDescricao())
                    .status(game.getStatus())
                    .build());
        });
        return response;
    }

    public TaskResponse addTask(TaskRequest taskRequest) {

        taskRepository.save(Task.builder()
                .descricao(taskRequest.getDescricao())
                .status(taskRequest.getStatus())
                .deadline(taskRequest.getDeadline())
                .user(taskRequest.getUser())
                .build());

        return TaskResponse.builder()
                .descricao(taskRequest.getDescricao())
                .status(taskRequest.getStatus())
                .deadline(taskRequest.getDeadline())
                .build();
    }

    public TaskResponse taskDetails(Long id) throws Exception {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            return TaskResponse.builder()
                    .descricao(task.get().getDescricao())
                    .status(task.get().getStatus())
                    .deadline(task.get().getDeadline())
                    .build();
        }else {
            throw new Exception("Esse game não existe");
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

        if(optional.isPresent()) {
            taskRepository.save(Task.builder()
                    .id(id)
                    .descricao(taskRequest.getDescricao())
                    .deadline(taskRequest.getDeadline())
                    .status(taskRequest.getStatus())
                    .user(taskRequest.getUser())
                    .build());

            return TaskResponse.builder()
                    .id(id)
                    .descricao(taskRequest.getDescricao())
                    .deadline(taskRequest.getDeadline())
                    .status(taskRequest.getStatus())
                    .build();
        }else{
            throw new Exception("Esse game não existe");
        }
    }


}
