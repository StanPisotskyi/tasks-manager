package tasks.manager.api.records;

import tasks.manager.api.entities.enums.TaskStatus;

import java.util.Date;

public record TaskListRecord(Long id, String title, Date createdAt, TaskStatus status, UserRecord assignedTo, ProjectRecord project) {
}
