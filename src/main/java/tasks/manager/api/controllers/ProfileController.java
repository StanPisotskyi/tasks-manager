package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.enums.TaskStatus;
import tasks.manager.api.factories.TaskRecordFactory;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.CountRecord;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.records.TaskListRecord;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.requests.AccountEditRequest;
import tasks.manager.api.requests.PasswordRequest;
import tasks.manager.api.responses.JwtAuthenticationResponse;
import tasks.manager.api.security.AuthenticationService;
import tasks.manager.api.security.PasswordService;
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
    private final AuthenticationService authenticationService;
    private final PasswordService passwordService;

    @GetMapping("/me")
    public UserRecord me() {
        return this.userRecordFactory.create(this.userService.getCurrentUser());
    }

    @PutMapping("/me")
    public JwtAuthenticationResponse me(@RequestBody @Valid AccountEditRequest request) {
        return this.authenticationService.update(request);
    }

    @PutMapping("/password")
    public DefaultRecord password(@RequestBody @Valid PasswordRequest request) {
        this.passwordService.update(this.userService.getCurrentUser(), request);

        return new DefaultRecord(true, "Password has been changed");
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

    @GetMapping("/tasks/count")
    public CountRecord tasksCount(
            @RequestParam(required = false) Long project
    ) {
        List<String> statuses = new ArrayList<>();
        statuses.add(String.valueOf(TaskStatus.NEW));
        statuses.add(String.valueOf(TaskStatus.IN_PROGRESS));

        return new CountRecord(
                this.taskService.getTasksTotalAmount(
                        statuses,
                        project,
                        this.userService.getCurrentUser().getId()
                )
        );
    }
}
