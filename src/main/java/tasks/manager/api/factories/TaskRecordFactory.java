package tasks.manager.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;
import tasks.manager.api.records.TaskRecord;
import tasks.manager.api.records.UserRecord;

@Service
@RequiredArgsConstructor
public class TaskRecordFactory {
    private final UserRecordFactory userRecordFactory;
    private final ProjectRecordFactory projectRecordFactory;

    public TaskRecord create(Task task) {
        UserRecord assignedToRecord = null;

        if (task.getAssignedTo() != null) {
            assignedToRecord = this.userRecordFactory.create(task.getAssignedTo());
        }

        return new TaskRecord(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getStatus(),
                this.userRecordFactory.create(task.getCreatedBy()),
                assignedToRecord,
                this.projectRecordFactory.create(task.getProject())
        );
    }
}
