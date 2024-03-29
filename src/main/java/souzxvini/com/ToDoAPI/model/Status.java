package souzxvini.com.ToDoAPI.model;

public enum Status {
    TO_DO ("TO DO"),
    DONE ("DONE"),
    NOT_STARTED ("NOT STARTED"),

    EXPIRED ("EXPIRED");

    public String getName() {
        return name;
    }

    private final String name;

    Status(String name){
        this.name = name;
    }
}