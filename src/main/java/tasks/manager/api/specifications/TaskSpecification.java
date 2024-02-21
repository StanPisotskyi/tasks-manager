package tasks.manager.api.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import tasks.manager.api.entities.Project;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;

import java.util.List;

public class TaskSpecification {
    private TaskSpecification() {
    }

    public static Specification<Task> setStatuses(List<String> statuses) {
        return (root, query, builder) -> root.get("status").in(statuses);
    }

    public static Specification<Task> setProject(Long project) {
        return (root, query, builder) -> {
            Join<Project, Task> join = root.join("project");

            return builder.equal(join.get("id"), project);
        };
    }

    public static Specification<Task> setUser(Long user) {
        return (root, query, builder) -> {
            Join<User, Task> join = root.join("assignedTo");

            return builder.equal(join.get("id"), user);
        };
    }
}
