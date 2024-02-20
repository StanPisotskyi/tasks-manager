package tasks.manager.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;
import tasks.manager.api.records.TaskListRecord;
import tasks.manager.api.records.TaskRecord;
import tasks.manager.api.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

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

    public List<TaskListRecord> create(List<Task> tasks) {
        List<TaskListRecord> res = new ArrayList<>();

        for (Task task : tasks) {
            UserRecord assignedToRecord = null;

            if (task.getAssignedTo() != null) {
                assignedToRecord = this.userRecordFactory.create(task.getAssignedTo());
            }

            TaskListRecord record = new TaskListRecord(
                    task.getId(),
                    task.getTitle(),
                    task.getCreatedAt(),
                    task.getStatus(),
                    assignedToRecord,
                    this.projectRecordFactory.create(task.getProject())
            );

            res.add(record);
        }

        return res;
    }
}
