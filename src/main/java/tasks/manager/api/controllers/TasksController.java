package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasks.manager.api.factories.TaskRecordFactory;
import tasks.manager.api.records.TaskRecord;
import tasks.manager.api.requests.TaskRequest;
import tasks.manager.api.services.TaskService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TaskRecordFactory taskRecordFactory;
    private final TaskService taskService;

    @PostMapping("")
    public TaskRecord create(@RequestBody @Valid TaskRequest request) {
        return this.taskRecordFactory.create(this.taskService.create(request));
    }
}
