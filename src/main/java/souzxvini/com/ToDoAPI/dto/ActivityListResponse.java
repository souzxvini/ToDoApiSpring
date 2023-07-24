package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityListResponse {

    private ActivityDayResponse day_1;
    private ActivityDayResponse day_2;
    private ActivityDayResponse day_3;
    private ActivityDayResponse day_4;
    private ActivityDayResponse day_5;
    private ActivityDayResponse day_6;
    private ActivityDayResponse day_7;

}
