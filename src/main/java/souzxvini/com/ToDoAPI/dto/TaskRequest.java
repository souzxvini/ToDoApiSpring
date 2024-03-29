package souzxvini.com.ToDoAPI.dto;

import lombok.*;
import souzxvini.com.ToDoAPI.model.Priority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    @NotBlank
    private String description;

    @NotNull
    private Integer categoryId;

    private String initialDate;

    private String deadline;

    @NotBlank
    private String priority;

}
