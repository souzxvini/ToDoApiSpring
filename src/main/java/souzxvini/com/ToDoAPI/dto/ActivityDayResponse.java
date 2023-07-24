package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import souzxvini.com.ToDoAPI.model.Activity;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDayResponse {

    private LocalDate currentDay;
    private String currentDayName;
    private List<ActivityResponse> doneTasks;

}
