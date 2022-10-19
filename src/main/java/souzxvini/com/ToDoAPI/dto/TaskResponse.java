package souzxvini.com.ToDoAPI.dto;

import lombok.*;
import souzxvini.com.ToDoAPI.model.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private Long id;

    private String descricao;

    private Status status;

    private LocalDateTime deadline;

}
