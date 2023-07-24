package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TasksFilterRequest {

    @NotNull
    private Long categoryId;
    private String priority;
    private LocalDate initialDate;
    private LocalDate deadline;

}
