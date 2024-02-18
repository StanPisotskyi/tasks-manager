package tasks.manager.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Project;
import tasks.manager.api.records.ProjectRecord;

@Service
@RequiredArgsConstructor
public class ProjectRecordFactory {
    private final UserRecordFactory userRecordFactory;

    public ProjectRecord create (Project project) {
        return new ProjectRecord(
                project.getId(),
                project.getTitle(),
                project.getAlias(),
                project.getStatus(),
                project.getCreatedAt(),
                this.userRecordFactory.create(project.getCreatedBy())
        );
    }
}
