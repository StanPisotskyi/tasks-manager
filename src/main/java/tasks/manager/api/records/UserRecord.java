package tasks.manager.api.records;

import tasks.manager.api.entities.enums.Role;

public record UserRecord(Long id, String firstName, String lastName, String username, String email, Role role) {
}
