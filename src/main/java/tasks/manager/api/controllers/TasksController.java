package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.Task;
import tasks.manager.api.factories.TaskRecordFactory;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.records.TaskListRecord;
import tasks.manager.api.records.TaskRecord;
import tasks.manager.api.requests.TaskRequest;
import tasks.manager.api.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TaskRecordFactory taskRecordFactory;
    private final TaskService taskService;

    @GetMapping("")
    public List<TaskListRecord> getList(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page, @RequestParam(required = false) String status) {
        return this.taskRecordFactory.create(this.taskService.getList(status, limit, page));
    }

    @GetMapping("/{id}")
    public TaskRecord getOne(@PathVariable("id") Task task) {
        return this.taskRecordFactory.create(task);
    }

    @PostMapping("")
    public TaskRecord create(@RequestBody @Valid TaskRequest request) {
        return this.taskRecordFactory.create(this.taskService.create(request));
    }

    @PutMapping("/{id}")
    public TaskRecord update(@PathVariable("id") Task task, @RequestBody @Valid TaskRequest request) {
        return this.taskRecordFactory.create(this.taskService.update(task, request));
    }

    @DeleteMapping("/{id}")
    public DefaultRecord delete(@PathVariable("id") Task task) {
        this.taskService.deleteById(task);

        return new DefaultRecord(true, "Task has been deleted");
    }
}
