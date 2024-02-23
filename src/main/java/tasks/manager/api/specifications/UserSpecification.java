package tasks.manager.api.specifications;

import org.springframework.data.jpa.domain.Specification;
import tasks.manager.api.entities.User;
import tasks.manager.api.entities.enums.Role;

public class UserSpecification {
    private UserSpecification() {
    }

    public static Specification<User> checkIsAdmin() {
        return (root, query, builder) -> builder.equal(root.get("role"), Role.ROLE_ADMIN);
    }
}
