package souzxvini.com.ToDoAPI.model;

public enum Priority {
    HIGH ("1"),
    MEDIUM ("2"),
    LOW ("3");

    public String getName() {
        return name;
    }

    private final String name;

    Priority(String name){
        this.name = name;
    }
}