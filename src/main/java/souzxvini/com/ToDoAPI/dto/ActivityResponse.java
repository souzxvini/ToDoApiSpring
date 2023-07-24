package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponse {

    private LocalDate conclusionDate;
    private String taskDescription;
    private String categoryName;

}
