package tasks.manager.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerRequest {
    @Size(min = 2, message = "Text has to contain more than 2 characters")
    @NotBlank(message = "Text cannot be empty")
    private String text;

    @NotNull(message = "Reply id cannot be empty")
    private Long replyId;
}
