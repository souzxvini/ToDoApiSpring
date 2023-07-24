package souzxvini.com.ToDoAPI.model;

public enum ConclusionStatus {
    OUT_OF_TIME ("DONE OUT OF TIME"),
    WITHIN_TIME ("DONE WITHIN TIME"),
    BEFORE_START_TIME ("DONE BEFORE START TIME");

    public String getName() {
        return name;
    }

    private final String name;

    ConclusionStatus(String name){
        this.name = name;
    }
}