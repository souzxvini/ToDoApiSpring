package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private Integer categoryId;
    private String name;
    private List<TaskResponse> doneTasks;
    private List<TaskResponse> todoTasks;
    private List<TaskResponse> notStartedTasks;
    private List<TaskResponse> expiredTasks;


}
