package souzxvini.com.ToDoAPI.util;

import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor
public class DateUtil {

    public static LocalDate toLocalDate(String value, String format){
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }
    public static LocalTime toLocalTime(String value){

        if(value.contains("PM") || value.contains("AM")){
            return LocalTime.parse(value, DateTimeFormatter.ofPattern("hh:mm a"));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(value, formatter);
    }

    public static String toLocalTimeString(String value){
        LocalTime time = LocalTime.parse(value);

        // Criar um DateTimeFormatter personalizado para o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Formatando o objeto LocalTime usando o formatter
        return time.format(formatter);
    }




}
