package souzxvini.com.ToDoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeForgotPasswordRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String randomCode;

}
