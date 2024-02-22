package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Project;
import tasks.manager.api.repositories.ProjectRepository;
import tasks.manager.api.requests.ProjectRequest;
import tasks.manager.api.security.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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

    public Project findOneById(Long id) {
        if (!this.repository.existsById(id)) {
            throw new RuntimeException(STR."Project with id[\{id}] is not found");
        }

        Optional<Project> findByRes = this.repository.findById(id);

        ArrayList<Project> res = new ArrayList<>();
        findByRes.ifPresent(res::add);

        return res.getFirst();
    }

    public void deleteById(Project project) {
        if (!this.repository.existsById(project.getId())) {
            throw new RuntimeException(STR."Project with id[\{project.getId()}] is not found");
        }

        this.repository.deleteById(project.getId());
    }
}
