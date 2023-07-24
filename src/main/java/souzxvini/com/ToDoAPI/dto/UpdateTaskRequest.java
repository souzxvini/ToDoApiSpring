package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    @NotNull
    private Integer taskId;

    @NotBlank
    private String description;

    private String initialDate;

    private String deadline;

    @NotBlank
    private String priority;

}
