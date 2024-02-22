package tasks.manager.api.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tasks.manager.api.entities.enums.Role;

@Data
public class UserEditRequest {
    @Size(min = 2, max = 50, message = "First name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Size(min = 5, max = 50, message = "Username has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Size(min = 5, max = 255, message = "Email has to contain more than 5 and less than 255 characters")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 1, message = "Role has to contain more than 1 character")
    @NotBlank(message = "Role cannot be empty")
    private String role;

    public Role getRole() {
        return Role.findByRole(this.role);
    }
}
