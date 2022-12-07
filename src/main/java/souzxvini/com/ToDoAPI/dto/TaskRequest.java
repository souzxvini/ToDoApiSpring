package souzxvini.com.ToDoAPI.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    @NotBlank
    private String description;

}
