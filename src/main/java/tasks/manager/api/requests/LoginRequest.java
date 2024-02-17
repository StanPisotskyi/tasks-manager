package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Size(min = 5, max = 50, message = "Username has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Size(max = 255, message = "Password has to contain less than 256 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;

}