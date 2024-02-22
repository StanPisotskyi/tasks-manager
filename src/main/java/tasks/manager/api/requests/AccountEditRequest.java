package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountEditRequest {
    @Size(min = 2, max = 50, message = "First name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name has to contain more than 2 and less than 50 characters")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Size(min = 5, max = 50, message = "Username has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;
}
