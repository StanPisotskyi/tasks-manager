package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.Project;
import tasks.manager.api.factories.ProjectRecordFactory;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.records.ProjectRecord;
import tasks.manager.api.requests.ProjectRequest;
import tasks.manager.api.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final ProjectRecordFactory projectRecordFactory;

    @GetMapping("")
    public List<ProjectRecord> getAll() {
        return this.projectRecordFactory.create(this.projectService.getAll());
    }

    @GetMapping("/{id}")
    public ProjectRecord getOne(@PathVariable("id") Project project) {
        return this.projectRecordFactory.create(project);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectRecord create(@RequestBody @Valid ProjectRequest request) {
        return this.projectRecordFactory.create(this.projectService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectRecord update(@PathVariable("id") Project project, @RequestBody @Valid ProjectRequest request) {
        return this.projectRecordFactory.create(this.projectService.update(project, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DefaultRecord delete(@PathVariable("id") Project project) {
        this.projectService.deleteById(project);

        return new DefaultRecord(true, "Project has been deleted");
    }
}
