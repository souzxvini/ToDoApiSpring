package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationsResponse {

    private Integer id;
    private String title;
    private String description;
    private LocalDate date;
    private Boolean visualized;
    private Boolean clicked;
    private List<TaskResponse> tasks;
}
