package souzxvini.com.ToDoAPI.dto;

import lombok.*;
import souzxvini.com.ToDoAPI.model.Category;
import souzxvini.com.ToDoAPI.model.ConclusionStatus;
import souzxvini.com.ToDoAPI.model.Priority;
import souzxvini.com.ToDoAPI.model.Status;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private Integer id;

    private String description;

    private LocalDate initialDate;

    private LocalDate deadline;

    private String priority;

    private Status status;

    private ConclusionStatus conclusionStatus;

    private Integer categoryId;

    private String categoryName;

}
