package tasks.manager.api.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Size(min = 2, max = 50, message = "First name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "First name cannot be empty")
    protected String firstName;

    @Size(min = 2, max = 50, message = "Last name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "Last name cannot be empty")
    protected String lastName;

    @Size(min = 5, max = 50, message = "Username has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Username cannot be empty")
    protected String username;

    @Size(min = 5, max = 255, message = "Email has to contain more than 5 and less than 255 characters")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is invalid")
    protected String email;

    @Size(max = 255, message = "Password has to contain less than 256 characters")
    @NotBlank(message = "Password cannot be empty")
    protected String password;
}
