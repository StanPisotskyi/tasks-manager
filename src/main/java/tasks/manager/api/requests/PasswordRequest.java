package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;

@Data
public class PasswordRequest {
    @Size(max = 255, message = "Password has to contain less than 256 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Confirm cannot be empty")
    private String confirm;

    public boolean equals() {
        return Objects.equals(this.password, this.confirm);
    }
}
