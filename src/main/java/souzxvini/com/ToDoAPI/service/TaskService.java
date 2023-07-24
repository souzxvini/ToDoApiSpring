package souzxvini.com.ToDoAPI.service;

import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.TaskRequest;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.dto.TasksFilterRequest;
import souzxvini.com.ToDoAPI.dto.UpdateTaskRequest;
import souzxvini.com.ToDoAPI.model.Activity;
import souzxvini.com.ToDoAPI.model.Category;
import souzxvini.com.ToDoAPI.model.ConclusionStatus;
import souzxvini.com.ToDoAPI.model.Priority;
import souzxvini.com.ToDoAPI.model.Status;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.ActivityRepository;
import souzxvini.com.ToDoAPI.repository.CategoryRepository;
import souzxvini.com.ToDoAPI.repository.TaskRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;
import souzxvini.com.ToDoAPI.util.DateUtil;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ActivityRepository activityRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    public TaskResponse addTask(TaskRequest taskRequest, Principal principal) throws Exception {

        Optional<Category> optional = categoryRepository.findByUserEmailAndId(principal.getName(), taskRequest.getCategoryId());

        if (!optional.isEmpty()) {

            Category category = optional.get();

            LocalDate initialDate = null;
            LocalDate deadline = null;

            if (taskRequest.getInitialDate() != null) {
                initialDate = DateUtil.toLocalDate(taskRequest.getInitialDate(), "dd-MM-yyyy");
            }
            if (taskRequest.getDeadline() != null) {
                deadline = DateUtil.toLocalDate(taskRequest.getDeadline(), "dd-MM-yyyy");
            }

            if (initialDate == null && deadline == null) {
                Task task = rowMapperTask(taskRequest, Status.TO_DO, initialDate, deadline, category);
                taskRepository.save(task);

                category.getTasks().add(task);
                categoryRepository.save(category);

                return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
            }

            if (taskRequest.getInitialDate() != null && taskRequest.getDeadline() != null) {
                // verifica se a data de inicio é maior que a atual
                if (initialDate.isAfter(LocalDate.now())) {
                    Task task = rowMapperTask(taskRequest, Status.NOT_STARTED, initialDate, deadline, category);
                    taskRepository.save(task);

                    category.getTasks().add(task);
                    categoryRepository.save(category);

                    return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                } else {
                    // verifica se a data final é maior ou igual a data atual, se não for, quer dizer que ja passou
                    if (deadline.isAfter(LocalDate.now()) || deadline.isEqual(LocalDate.now())) {
                        Task task = rowMapperTask(taskRequest, Status.TO_DO, initialDate, deadline, category);
                        taskRepository.save(task);

                        category.getTasks().add(task);
                        categoryRepository.save(category);

                        return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                    } else {
                        Task task = rowMapperTask(taskRequest, Status.EXPIRED, initialDate, deadline, category);
                        taskRepository.save(task);

                        category.getTasks().add(task);
                        categoryRepository.save(category);

                        return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                    }
                }
            }

            if (initialDate != null && deadline == null) {
                if (initialDate.isAfter(LocalDate.now())) {
                    Task task = rowMapperTask(taskRequest, Status.NOT_STARTED, initialDate, deadline, category);
                    taskRepository.save(task);

                    category.getTasks().add(task);
                    categoryRepository.save(category);

                    return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                } else {
                    Task task = rowMapperTask(taskRequest, Status.TO_DO, initialDate, deadline, category);
                    taskRepository.save(task);

                    category.getTasks().add(task);
                    categoryRepository.save(category);

                    return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                }
            }

            if (initialDate == null && deadline != null) {
                if (deadline.isBefore(LocalDate.now())) {
                    Task task = rowMapperTask(taskRequest, Status.EXPIRED, initialDate, deadline, category);
                    taskRepository.save(task);

                    category.getTasks().add(task);
                    categoryRepository.save(category);

                    return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                } else {
                    Task task = rowMapperTask(taskRequest, Status.TO_DO, initialDate, deadline, category);
                    taskRepository.save(task);

                    category.getTasks().add(task);
                    categoryRepository.save(category);

                    return rowMapperTaskResponse(task, taskRequest, initialDate, deadline);
                }
            }
        }
        throw new UserPrincipalNotFoundException("There isn't any category with this id");
    }

    public void deleteTask(Integer id) throws Exception {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task taskToDelete = task.get();
            Category category = taskToDelete.getCategory();
            category.getTasks().remove(taskToDelete);
            taskRepository.deleteById(id);
        } else {
            throw new Exception("Essa tarefa não existe");
        }
    }

    public TaskResponse getTask(Integer id) throws Exception {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return TaskResponse.builder()
                    .id(task.get().getId())
                    .description(task.get().getDescription())
                    .priority(task.get().getPriority())
                    .initialDate(task.get().getInitialDate())
                    .deadline(task.get().getDeadline())
                    .status(task.get().getStatus())
                    .categoryId(task.get().getCategory().getId())
                    .categoryName(task.get().getCategory().getName())
                    .build();
        } else {
            throw new Exception("Essa tarefa não existe");
        }
    }

    public void updateTask(UpdateTaskRequest taskRequest, Principal principal) throws Exception {
        String username = principal.getName();

        Optional<Task> optional = taskRepository.findByCategoryUserEmailAndId(username, taskRequest.getTaskId());

        if (!(optional.isEmpty())) {

            Task task = optional.get();

            LocalDate initialDate = null;
            LocalDate deadline = null;

            if (taskRequest.getInitialDate() != null) {
                initialDate = DateUtil.toLocalDate(taskRequest.getInitialDate(), "dd-MM-yyyy");
            }
            if (taskRequest.getDeadline() != null) {
                deadline = DateUtil.toLocalDate(taskRequest.getDeadline(), "dd-MM-yyyy");
            }

            if (initialDate == null && deadline == null) {
                task.setDescription(taskRequest.getDescription());
                task.setInitialDate(initialDate);
                task.setDeadline(deadline);
                task.setPriority(taskRequest.getPriority());
                task.setStatus(Status.TO_DO);
                taskRepository.save(task);
            } else if (initialDate != null && deadline != null) {
                // verifica se a data de inicio é maior que a atual
                if (initialDate.isAfter(LocalDate.now())) {
                    task.setDescription(taskRequest.getDescription());
                    task.setInitialDate(initialDate);
                    task.setDeadline(deadline);
                    task.setPriority(taskRequest.getPriority());
                    task.setStatus(Status.NOT_STARTED);
                    taskRepository.save(task);
                } else {
                    // verifica se a data final é maior ou igual a data atual, se não for, quer dizer que ja passou
                    if (deadline.isAfter(LocalDate.now()) || deadline.isEqual(LocalDate.now())) {
                        task.setDescription(taskRequest.getDescription());
                        task.setInitialDate(initialDate);
                        task.setDeadline(deadline);
                        task.setPriority(taskRequest.getPriority());
                        task.setStatus(Status.TO_DO);
                        taskRepository.save(task);
                    } else {
                        task.setDescription(taskRequest.getDescription());
                        task.setInitialDate(initialDate);
                        task.setDeadline(deadline);
                        task.setPriority(taskRequest.getPriority());
                        task.setStatus(Status.EXPIRED);
                        taskRepository.save(task);
                    }
                }
            }

            if (initialDate != null && deadline == null) {
                if (initialDate.isAfter(LocalDate.now())) {
                    task.setDescription(taskRequest.getDescription());
                    task.setInitialDate(initialDate);
                    task.setDeadline(deadline);
                    task.setPriority(taskRequest.getPriority());
                    task.setStatus(Status.NOT_STARTED);
                    taskRepository.save(task);
                } else {
                    task.setDescription(taskRequest.getDescription());
                    task.setInitialDate(initialDate);
                    task.setDeadline(deadline);
                    task.setPriority(taskRequest.getPriority());
                    task.setStatus(Status.TO_DO);
                    taskRepository.save(task);
                }
            }

            if (initialDate == null && deadline != null) {
                if (deadline.isBefore(LocalDate.now())) {
                    task.setDescription(taskRequest.getDescription());
                    task.setInitialDate(initialDate);
                    task.setDeadline(deadline);
                    task.setPriority(taskRequest.getPriority());
                    task.setStatus(Status.EXPIRED);
                    taskRepository.save(task);
                } else {
                    task.setDescription(taskRequest.getDescription());
                    task.setInitialDate(initialDate);
                    task.setDeadline(deadline);
                    task.setPriority(taskRequest.getPriority());
                    task.setStatus(Status.TO_DO);
                    taskRepository.save(task);
                }
            }
        } else {
            throw new Exception("This task doesn't exists.");
        }
    }

    public void changeTaskStatus(Integer id, Principal principal) throws Exception {
        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(username);
        Optional<Task> optional = taskRepository.findById(id);

        if (!optionalUser.isEmpty()) {
            User user = optionalUser.get();
            if (!(optional.isEmpty())) {
                Task task = optional.get();
                if (task.getStatus().equals(Status.TO_DO)) {
                    changeTaskStatus(task, Status.DONE, ConclusionStatus.WITHIN_TIME);
                    saveActivity(task, user);
                } else if (task.getStatus().equals(Status.NOT_STARTED)) {
                    changeTaskStatus(task, Status.DONE, ConclusionStatus.BEFORE_START_TIME);
                    saveActivity(task, user);
                } else if (task.getStatus().equals(Status.EXPIRED)) {
                    changeTaskStatus(task, Status.DONE, ConclusionStatus.OUT_OF_TIME);
                    saveActivity(task, user);
                } else if (task.getStatus().equals(Status.DONE)) {
                    if (task.getConclusionStatus().equals(ConclusionStatus.WITHIN_TIME)) {
                        changeTaskStatus(task, Status.TO_DO, null);
                        deleteActivity(task);
                    } else if (task.getConclusionStatus().equals(ConclusionStatus.BEFORE_START_TIME)) {
                        changeTaskStatus(task, Status.NOT_STARTED, null);
                        deleteActivity(task);
                    } else if (task.getConclusionStatus().equals(ConclusionStatus.OUT_OF_TIME)) {
                        changeTaskStatus(task, Status.EXPIRED, null);
                        deleteActivity(task);
                    }
                }
            } else {
                throw new Exception("This task doesn't exists.");
            }
        } else{
            throw new Exception("This user doesn't exists.");
        }

    }

    private void changeTaskStatus(Task task, Status status, ConclusionStatus conclusionStatus){
        task.setStatus(status);
        task.setConclusionStatus(conclusionStatus);
        taskRepository.save(task);
    }

    private void saveActivity(Task task, User user){
        activityRepository.save(Activity.builder()
                .conclusionDate(LocalDate.now())
                .taskId(task.getId())
                .taskDescription(task.getDescription())
                .categoryName(task.getCategory().getName())
                .user(user)
                .build());
    }
    private void deleteActivity(Task task){
        Activity activity = activityRepository.findByTaskId(task.getId());
        activityRepository.delete(activity);
    }


    private TaskResponse rowMapperTaskResponse(Task task, TaskRequest taskRequest, LocalDate initialDate, LocalDate deadline) {
        return TaskResponse.builder()
                .id(task.getId())
                .description(taskRequest.getDescription())
                .initialDate(initialDate)
                .deadline(deadline)
                .priority(taskRequest.getPriority())
                .status(Status.TO_DO)
                .categoryId(task.getCategory().getId())
                .build();
    }

    private Task rowMapperTask(TaskRequest taskRequest, Status status, LocalDate initialDate, LocalDate deadline, Category category) {
        return Task.builder()
                .description(taskRequest.getDescription())
                .status(status)
                .initialDate(initialDate)
                .deadline(deadline)
                .category(category)
                .priority(taskRequest.getPriority())
                .build();
    }

}
