package souzxvini.com.ToDoAPI.service;

import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.NotificationsResponse;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.model.Notification;
import souzxvini.com.ToDoAPI.model.Status;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.NotificationRepository;
import souzxvini.com.ToDoAPI.repository.TaskRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;
import souzxvini.com.ToDoAPI.util.DateUtil;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationsResponse> getAllNotifications(Principal principal) {
        String username = principal.getName();

        List<Notification> notifications = notificationRepository.findByUserEmail(username);

        List<NotificationsResponse> response = new ArrayList<>();

        notifications.stream().forEach(n -> {
            List<TaskResponse> tasks = new ArrayList<>();

            n.getTasks().stream().forEach(t -> {
                tasks.add(TaskResponse.builder()
                                .id(t.getId())
                                .categoryName(t.getCategory().getName())
                                .categoryId(t.getCategory().getId())
                                .description(t.getDescription())
                        .build());
            });

            response.add(NotificationsResponse.builder()
                            .id(n.getId())
                            .date(n.getDate())
                            .title(n.getTitle())
                            .description(n.getDescription())
                            .visualized(n.getVisualized())
                            .clicked(n.getClicked())
                            .tasks(tasks)
                    .build());
        });

        return response.stream().sorted(Comparator.comparing(NotificationsResponse :: getId).reversed()).collect(Collectors.toList());
    }

    public void setAllAsVisualized(Principal principal){
        String username = principal.getName();

        List<Notification> notifications = notificationRepository.findByUserEmail(username);

        notifications.stream().forEach(n -> {
            if(n.getVisualized() == false){
                n.setVisualized(true);
                notificationRepository.save(n);
            }
        });
    }

    public void setNotificationAsClicked(Integer id, Principal principal) throws Exception{
        String username = principal.getName();

        Optional<Notification> optional = notificationRepository.findByUserEmailAndId(username, id);

        if(!optional.isEmpty()){
            optional.get().setClicked(true);
            notificationRepository.save(optional.get());
        } else{
            new Exception("Essa notificação não existe para esse usuário");
        }
    }

    public void createNotification(Principal principal){
        String username = principal.getName();

        Optional<User> user = userRepository.findByEmail(username);

        List<Task> tasks = taskRepository.findByCategoryId(1);

        List<Task> filtered = tasks.stream().filter(t -> t.getStatus().equals(Status.DONE)).collect(Collectors.toList());

        notificationRepository.save(Notification.builder()
                        .title("Tarefas Concluidas!")
                        .description("Você possui algumas tarefas concluidas")
                        .date(LocalDate.now())
                        .tasks(filtered)
                        .user(user.get())
                        .clicked(false)
                        .visualized(false)
                .build());
    }

}
