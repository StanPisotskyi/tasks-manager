package tasks.manager.api.records;

import tasks.manager.api.entities.Project;
import tasks.manager.api.entities.User;
import tasks.manager.api.entities.enums.TaskStatus;

import java.util.Date;

public record TaskRecord(Long id, String title, String description, Date createdAt, Date updatedAt, TaskStatus status, UserRecord createdBy, UserRecord assignedTo, ProjectRecord project) {
}
