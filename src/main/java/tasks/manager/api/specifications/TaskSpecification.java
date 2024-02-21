package tasks.manager.api.specifications;

import org.springframework.data.jpa.domain.Specification;
import tasks.manager.api.entities.Task;

public class TaskSpecification {
    private TaskSpecification() {
    }

    public static Specification<Task> setStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }
}
