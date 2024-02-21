package tasks.manager.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tasks.manager.api.entities.enums.TaskStatus;
import tasks.manager.api.factories.TaskRecordFactory;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.TaskListRecord;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.security.UserService;
import tasks.manager.api.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final UserRecordFactory userRecordFactory;
    private final TaskRecordFactory taskRecordFactory;
    private final TaskService taskService;

    @GetMapping("/me")
    public UserRecord me() {
        return this.userRecordFactory.create(this.userService.getCurrentUser());
    }

    @GetMapping("/tasks")
    public List<TaskListRecord> tasks(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Long project
    ) {
        List<String> statuses = new ArrayList<>();
        statuses.add(String.valueOf(TaskStatus.NEW));
        statuses.add(String.valueOf(TaskStatus.IN_PROGRESS));

        return this.taskRecordFactory.create(
                this.taskService.getList(
                        statuses,
                        project,
                        this.userService.getCurrentUser().getId(),
                        limit,
                        page
                )
        );
    }
}
