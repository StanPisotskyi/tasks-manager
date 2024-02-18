package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tasks.manager.api.entities.enums.TaskStatus;

@Data
public class TaskRequest {
    @Size(min = 5, max = 50, message = "Title has to contain more than 5 and less than 50 characters")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Size(min = 10, message = "Description has to contain more than 10 characters")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Size(min = 1, message = "Status has to contain more than 1 character")
    @NotBlank(message = "Status cannot be empty")
    private String status;

    @NotNull(message = "Project id cannot be empty")
    private Long projectId;

    private Long assignedToId;

    public TaskStatus getStatus() {
        return TaskStatus.findByStatus(status);
    }
}
