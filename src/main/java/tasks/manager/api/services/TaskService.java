package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;
import tasks.manager.api.repositories.TaskRepository;
import tasks.manager.api.requests.TaskRequest;
import tasks.manager.api.security.UserService;
import tasks.manager.api.specifications.TaskSpecification;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;

    private final int limit = 50;
    private final int page = 0;

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

    public Task update(Task task, TaskRequest request) {
        User assignedTo = null;

        if (request.getAssignedToId() != null) {
            assignedTo = this.userService.findOneById(request.getAssignedToId());
        }

        task.setUpdatedAt(new Date());
        task.setAssignedTo(assignedTo);
        task.setProject(this.projectService.findOneById(request.getProjectId()));

        BeanUtils.copyProperties(request, task, "id", "createdBy", "createdAt", "updatedAt", "assignedTo", "project");

        return save(task);
    }

    public void deleteById(Task task) {
        if (!this.taskRepository.existsById(task.getId())) {
            throw new RuntimeException(STR."Task with id[\{task.getId()}] is not found");
        }

        this.taskRepository.deleteById(task.getId());
    }

    public List<Task> getList(String status, Integer limit, Integer page) {

        if (page == null || page < 0) {
            page = this.page;
        }

        if (limit == null || limit < 1) {
            limit = this.limit;
        }

        Specification<Task> filters = Specification.where(StringUtils.isBlank(status) ? null : TaskSpecification.setStatus(status.toUpperCase()));
        Pageable pagination = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        return this.taskRepository.findAll(filters, pagination).getContent();
    }
}
