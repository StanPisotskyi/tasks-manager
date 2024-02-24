package tasks.manager.api.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.entities.Task;

public class CommentSpecification {
    private CommentSpecification() {}

    public static Specification<Comment> setTask(Task task) {
        return (root, query, builder) -> {
            Join<Comment, Task> join = root.join("task");

            return builder.equal(join.get("id"), task.getId());
        };
    }
}
