package souzxvini.com.ToDoAPI.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse{

    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
