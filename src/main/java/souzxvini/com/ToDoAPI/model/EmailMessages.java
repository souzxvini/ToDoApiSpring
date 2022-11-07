package souzxvini.com.ToDoAPI.model;

public class EmailMessages {

    public static String createTittle(User user){
        return user.getName() + ", your request to change your password was received!";
    }

    public static String messageToUser(User user, String code){
        return "Hi " + user.getName() +
                ", your 6 digit confirmation code is: " + code;
    }
}
