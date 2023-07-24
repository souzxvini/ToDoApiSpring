package souzxvini.com.ToDoAPI.service;

import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.CategoryRequest;
import souzxvini.com.ToDoAPI.dto.CategoryResponse;
import souzxvini.com.ToDoAPI.dto.ProgressResponse;
import souzxvini.com.ToDoAPI.dto.TaskResponse;
import souzxvini.com.ToDoAPI.model.Category;
import souzxvini.com.ToDoAPI.model.Status;
import souzxvini.com.ToDoAPI.model.Task;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.CategoryRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest, Principal principal) throws Exception {

        String username = principal.getName();

        Optional<User> user = userRepository.findByEmail(username);

        Category category = categoryRepository.save(Category.builder()
                .name(categoryRequest.getName())
                .user(user.get())
                .build());

        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }

    public CategoryResponse getCategory(Integer id, Principal principal) throws Exception {
        String username = principal.getName();

        Optional<Category> category = categoryRepository.findByUserEmailAndId(username, id);

        if (!category.isEmpty()) {
            CategoryResponse response = new CategoryResponse();

            List<Task> tasks = (category.get().getTasks());
            List<TaskResponse> doneTaskResponse = new ArrayList<>();
            List<TaskResponse> todoTaskResponse = new ArrayList<>();
            List<TaskResponse> notStartedTasks = new ArrayList<>();
            List<TaskResponse> expiredTasks = new ArrayList<>();

            if (!tasks.isEmpty()) {
                tasks.stream().forEach(task -> {
                    if (task.getStatus() == Status.DONE) doneTaskResponse.add(rowMapper(task));
                    if (task.getStatus() == Status.TO_DO) todoTaskResponse.add(rowMapper(task));
                    if (task.getStatus() == Status.NOT_STARTED) notStartedTasks.add(rowMapper(task));
                    if (task.getStatus() == Status.EXPIRED) expiredTasks.add(rowMapper(task));
                });
            }

            return response.builder()
                    .name(category.get().getName())
                    .categoryId(category.get().getId())
                    .doneTasks(doneTaskResponse)
                    .todoTasks(todoTaskResponse)
                    .notStartedTasks(notStartedTasks)
                    .expiredTasks(expiredTasks)
                    .build();
        }
        throw new Exception("This category does not exists.");
    }

    public List<CategoryResponse> getCategories(Principal principal) throws Exception {
        String username = principal.getName();

        List<Category> categories = categoryRepository.findByUserEmail(username);

        List<CategoryResponse> response = new ArrayList<>();

        if (!categories.isEmpty()) {
            categories.stream().forEach(category -> {
                List<Task> tasks = (category.getTasks());
                List<TaskResponse> doneTaskResponse = new ArrayList<>();
                List<TaskResponse> todoTaskResponse = new ArrayList<>();
                List<TaskResponse> notStartedTasks = new ArrayList<>();
                List<TaskResponse> expiredTasks = new ArrayList<>();

                if (!tasks.isEmpty()) {
                    tasks.stream().forEach(task -> {
                        if (task.getStatus() == Status.DONE) doneTaskResponse.add(rowMapper(task));
                        if (task.getStatus() == Status.TO_DO) todoTaskResponse.add(rowMapper(task));
                        if (task.getStatus() == Status.NOT_STARTED) notStartedTasks.add(rowMapper(task));
                        if (task.getStatus() == Status.EXPIRED) expiredTasks.add(rowMapper(task));
                    });
                }
                response.add(CategoryResponse.builder()
                        .categoryId(category.getId())
                        .name(category.getName())
                        .doneTasks(doneTaskResponse)
                        .todoTasks(todoTaskResponse)
                        .notStartedTasks(notStartedTasks)
                        .expiredTasks(expiredTasks)
                        .build());
            });
        }
        return response;
    }

    public void deleteCategory(Integer id, Principal principal) throws Exception {
        String username = principal.getName();

        Optional<Category> category = categoryRepository.findByUserEmailAndId(username, id);

        if (!category.isEmpty()) {
            if (category.get().getTasks().isEmpty()) {
                categoryRepository.deleteById(id);
            } else {
                throw new Exception("Não é possivel excluir essa categoria");
            }
        } else {
            throw new Exception("This category does not exists.");
        }
    }

    public ProgressResponse getProgress(Principal principal) throws Exception {

        String userName = principal.getName();

        List<Category> categories = categoryRepository.findAllByUserEmail(userName);

        if (!categories.isEmpty()) {
            final int[] todoTasks = {0};
            final int[] doneTasks = {0};
            final int[] notStartedTasks = {0};
            final int[] expiredTasks = {0};
            categories.stream().forEach(c -> {
                List<Task> tasks = (c.getTasks());
                tasks.stream().forEach(t -> {
                    if (t.getStatus() == Status.DONE) doneTasks[0]++;
                    if (t.getStatus() == Status.TO_DO) todoTasks[0]++;
                    if (t.getStatus() == Status.NOT_STARTED) notStartedTasks[0]++;
                    if (t.getStatus() == Status.EXPIRED) expiredTasks[0]++;
                });
            });

            return ProgressResponse.builder()
                    .doneTasks(doneTasks[0])
                    .todoTasks(todoTasks[0])
                    .notStartedTasks(notStartedTasks[0])
                    .expiredTasks(expiredTasks[0])
                    .build();
        } else {
            return ProgressResponse.builder()
                    .doneTasks(0)
                    .todoTasks(0)
                    .notStartedTasks(0)
                    .expiredTasks(0)
                    .build();
        }
    }

    private TaskResponse rowMapper(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .status(task.getStatus())
                .conclusionStatus(task.getConclusionStatus())
                .description(task.getDescription())
                .categoryId(task.getCategory().getId())
                .deadline(task.getDeadline())
                .initialDate(task.getInitialDate())
                .priority(task.getPriority())
                .build();
    }
}
