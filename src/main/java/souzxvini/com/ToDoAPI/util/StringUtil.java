package souzxvini.com.ToDoAPI.util;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class StringUtil {

    public static String convertDayToPortuguese(String value){
        String convertedDay = null;

        switch (value.toLowerCase()) {
            case "sun":
                convertedDay = "Dom";
                break;
            case "mon":
                convertedDay = "Seg";
                break;
            case "tue":
                convertedDay = "Ter";
                break;
            case "wed":
                convertedDay = "Qua";
                break;
            case "thu":
                convertedDay = "Qui";
                break;
            case "fri":
                convertedDay = "Sex";
                break;
            case "sat":
                convertedDay = "Sáb";
                break;
        }

        return convertedDay;
    }

}
