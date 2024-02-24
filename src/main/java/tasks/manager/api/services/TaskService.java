package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tasks.manager.api.entities.Task;
import tasks.manager.api.entities.User;
import tasks.manager.api.repositories.TaskRepository;
import tasks.manager.api.requests.TaskRequest;
import tasks.manager.api.security.UserService;
import tasks.manager.api.specifications.TaskSpecification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;

    public static final int LIMIT = 50;
    public static final int PAGE = 0;

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

    public List<Task> getList(List<String> statuses, Long project, Long user, Integer limit, Integer page) {

        if (page == null || page < 0) {
            page = PAGE;
        }

        if (limit == null || limit < 1) {
            limit = LIMIT;
        }

        if (project == null) {
            project = 0L;
        }

        if (user == null) {
            user = 0L;
        }

        Specification<Task> filters = Specification
                .where(CollectionUtils.isEmpty(statuses) ? null : TaskSpecification.setStatuses(statuses))
                .and(project == 0 ? null : TaskSpecification.setProject(project))
                .and(user == 0 ? null : TaskSpecification.setUser(user));
        Pageable pagination = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        return this.taskRepository.findAll(filters, pagination).getContent();
    }

    public Long getTasksTotalAmount(List<String> statuses, Long project, Long user) {
        if (project == null) {
            project = 0L;
        }

        if (user == null) {
            user = 0L;
        }

        Specification<Task> filters = Specification
                .where(CollectionUtils.isEmpty(statuses) ? null : TaskSpecification.setStatuses(statuses))
                .and(project == 0 ? null : TaskSpecification.setProject(project))
                .and(user == 0 ? null : TaskSpecification.setUser(user));

        return this.taskRepository.count(filters);
    }

    public Task findOneById(Long id) {
        if (!this.taskRepository.existsById(id)) {
            throw new RuntimeException(STR."Task with id[\{id}] is not found");
        }

        Optional<Task> findByRes = this.taskRepository.findById(id);

        ArrayList<Task> res = new ArrayList<>();
        findByRes.ifPresent(res::add);

        return res.getFirst();
    }
}
