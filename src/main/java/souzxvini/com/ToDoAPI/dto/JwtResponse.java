package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse{

    private String jwttoken;
    private String email;
    private String name;

}
