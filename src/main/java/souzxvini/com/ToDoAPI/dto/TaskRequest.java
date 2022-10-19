package souzxvini.com.ToDoAPI.dto;

import com.sun.istack.NotNull;
import lombok.*;
import souzxvini.com.ToDoAPI.model.Status;
import souzxvini.com.ToDoAPI.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    @NotNull @NotBlank
    private String descricao;

    @NotNull @NotBlank
    private Status status;

    @NotNull @NotBlank
    private LocalDateTime deadline;

    @NotNull @NotBlank
    private User user;
}
