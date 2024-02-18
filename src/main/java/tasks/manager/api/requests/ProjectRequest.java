package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {
    @Size(min = 2, max = 50, message = "Title has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Size(min = 2, max = 50, message = "Alias has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Alias cannot be empty")
    private String alias;
}
