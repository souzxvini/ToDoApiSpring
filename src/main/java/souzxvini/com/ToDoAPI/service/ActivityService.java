package souzxvini.com.ToDoAPI.service;

import org.springframework.stereotype.Service;
import souzxvini.com.ToDoAPI.dto.ActivityDayResponse;
import souzxvini.com.ToDoAPI.dto.ActivityListResponse;
import souzxvini.com.ToDoAPI.dto.ActivityResponse;
import souzxvini.com.ToDoAPI.model.Activity;
import souzxvini.com.ToDoAPI.model.User;
import souzxvini.com.ToDoAPI.repository.ActivityRepository;
import souzxvini.com.ToDoAPI.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final UserRepository userRepository;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public ActivityListResponse getActivity(Principal principal) throws Exception {
        String username = principal.getName();

        List<Activity> activities = activityRepository.findByUserEmail(username);

        List<ActivityResponse> listaDia1 = new ArrayList<>();
        List<ActivityResponse> listaDia2 = new ArrayList<>();
        List<ActivityResponse> listaDia3 = new ArrayList<>();
        List<ActivityResponse> listaDia4 = new ArrayList<>();
        List<ActivityResponse> listaDia5 = new ArrayList<>();
        List<ActivityResponse> listaDia6 = new ArrayList<>();
        List<ActivityResponse> listaDia7 = new ArrayList<>();
        ActivityListResponse response = new ActivityListResponse();

        if (!activities.isEmpty()) {
            activities.stream().forEach(a -> {
                System.out.println(LocalDate.now());
                if(a.getConclusionDate().isEqual(LocalDate.now())) listaDia1.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-1))) listaDia2.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-2)))  listaDia3.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-3))) listaDia4.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-4))) listaDia5.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-5))) listaDia6.add(rowMapperActivityResponse(a));

                if(a.getConclusionDate().isEqual(LocalDate.now().plusDays(-6))) listaDia7.add(rowMapperActivityResponse(a));
            });

            return fillActivityDayResponse(response, listaDia1, listaDia2, listaDia3, listaDia4, listaDia5, listaDia6, listaDia7,principal);
        }
        return fillActivityDayResponse(response, listaDia1, listaDia2, listaDia3, listaDia4, listaDia5, listaDia6, listaDia7, principal);
    }

    private ActivityResponse rowMapperActivityResponse(Activity a){
       return ActivityResponse.builder()
                .categoryName(a.getCategoryName())
                .conclusionDate(a.getConclusionDate())
                .taskDescription(a.getTaskDescription())
                .build();
    }

    private ActivityListResponse fillActivityDayResponse(ActivityListResponse response, List<ActivityResponse> listaDia1,
                                                        List<ActivityResponse> listaDia2,  List<ActivityResponse> listaDia3,
                                                        List<ActivityResponse> listaDia4,  List<ActivityResponse> listaDia5,
                                                        List<ActivityResponse> listaDia6,  List<ActivityResponse> listaDia7, Principal principal) throws Exception {
        response.setDay_1(rowMapperActivityDayResponse(listaDia1, 0, principal));
        response.setDay_2(rowMapperActivityDayResponse(listaDia2, 1, principal));
        response.setDay_3(rowMapperActivityDayResponse(listaDia3, 2, principal));
        response.setDay_4(rowMapperActivityDayResponse(listaDia4, 3, principal));
        response.setDay_5(rowMapperActivityDayResponse(listaDia5, 4, principal));
        response.setDay_6(rowMapperActivityDayResponse(listaDia6, 5, principal));
        response.setDay_7(rowMapperActivityDayResponse(listaDia7, 6, principal));

        return response;
    }

    private ActivityDayResponse rowMapperActivityDayResponse(List<ActivityResponse> lista, Integer daysToAdd, Principal principal) throws Exception {

        String username = principal.getName();

        Optional<User> user = userRepository.findByEmail(username);

        if(!user.isEmpty()){
            if(user.get().getLanguage().equals("pt")){
                return ActivityDayResponse.builder()
                        .currentDay(LocalDate.now().minusDays(daysToAdd))
                        .currentDayName(setFirstLetterUppercase(LocalDate.now().minusDays(daysToAdd).getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale.Builder().setLanguage("pt").setRegion("BR").build())))
                        .doneTasks(lista)
                        .build();
            }
            return ActivityDayResponse.builder()
                    .currentDay(LocalDate.now().minusDays(daysToAdd))
                    .currentDayName(setFirstLetterUppercase(LocalDate.now().minusDays(daysToAdd).getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale.Builder().setLanguage("en").setRegion("BR").build())))
                    .doneTasks(lista)
                    .build();
        }
        throw new Exception("User doesn't exists");
    }

    private String setFirstLetterUppercase(String dayName){
        return dayName.substring(0, 1).toUpperCase() + dayName.substring(1);
    }

}
