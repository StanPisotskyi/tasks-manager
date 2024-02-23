package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tasks.manager.api.entities.enums.Role;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCreateRequest extends RegisterRequest {
    @Size(min = 1, message = "Role has to contain more than 1 character")
    @NotBlank(message = "Role cannot be empty")
    private String role;

    public Role getRole() {
        return Role.findByRole(this.role);
    }
}
