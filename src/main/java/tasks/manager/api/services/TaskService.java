package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;
import tasks.manager.api.repositories.TaskRepository;
import tasks.manager.api.repositories.UserRepository;
import tasks.manager.api.requests.TaskRequest;
import tasks.manager.api.security.UserService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;

    public Task save(Task task) {
        return this.taskRepository.save(task);
    }

    public Task create(TaskRequest request) {
        User assignedTo = null;

        if (request.getAssignedToId() != null) {
            assignedTo = this.userService.findOneById(request.getAssignedToId());
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .createdAt(new Date())
                .createdBy(this.userService.getCurrentUser())
                .assignedTo(assignedTo)
                .project(this.projectService.findOneById(request.getProjectId()))
                .build();

        return save(task);
    }
}
