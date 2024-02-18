package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tasks.manager.api.entities.enums.Visibility;

@Data
public class ProjectRequest {
    @Size(min = 2, max = 50, message = "Title has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Size(min = 2, max = 50, message = "Alias has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Alias cannot be empty")
    private String alias;

    @Size(min = 1, message = "Status has to contain more than 1 character")
    @NotBlank(message = "Status cannot be empty")
    private String status;

    public Visibility getStatus() {
        return Visibility.findByStatus(status);
    }
}
