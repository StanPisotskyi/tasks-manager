package tasks.manager.api.specifications;

import org.springframework.data.jpa.domain.Specification;
import tasks.manager.api.entities.Task;

import java.util.List;

public class TaskSpecification {
    private TaskSpecification() {
    }

    public static Specification<Task> setStatuses(List<String> statuses) {
        return (root, query, builder) -> root.get("status").in(statuses);
    }
}
