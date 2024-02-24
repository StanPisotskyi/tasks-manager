package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentEditRequest {
    @Size(min = 2, message = "Text has to contain more than 2 characters")
    @NotBlank(message = "Text cannot be empty")
    private String text;
}
