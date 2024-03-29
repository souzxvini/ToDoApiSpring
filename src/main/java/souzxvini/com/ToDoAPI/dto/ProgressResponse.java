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
public class ProgressResponse {

    private Integer todoTasks;
    private Integer doneTasks;
    private Integer notStartedTasks;
    private Integer expiredTasks;

}
