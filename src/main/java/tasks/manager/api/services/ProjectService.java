package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Project;
import tasks.manager.api.repositories.ProjectRepository;
import tasks.manager.api.requests.ProjectRequest;
import tasks.manager.api.security.UserService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserService userService;
    private final ProjectRepository repository;

    public Project save(Project project) {
        return this.repository.save(project);
    }

    public Project create(ProjectRequest request) {
        Project project = Project.builder()
                .title(request.getTitle())
                .alias(request.getAlias())
                .status(request.getStatus())
                .createdAt(new Date())
                .createdBy(this.userService.getCurrentUser())
                .build();

        if (repository.existsByAlias(project.getAlias())) {
            throw new RuntimeException("Project with such alias already exists");
        }

        return save(project);
    }

    public Project update(Project project, ProjectRequest request) {
        BeanUtils.copyProperties(request, project, "id", "createdBy", "createdAt");

        return save(project);
    }

    public Iterable<Project> getAll() {
        return this.repository.findAll();
    }
}
