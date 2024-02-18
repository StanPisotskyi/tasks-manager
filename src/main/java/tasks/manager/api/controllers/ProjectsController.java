package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.Project;
import tasks.manager.api.factories.ProjectRecordFactory;
import tasks.manager.api.records.ProjectRecord;
import tasks.manager.api.requests.ProjectRequest;
import tasks.manager.api.services.ProjectService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final ProjectRecordFactory projectRecordFactory;

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
}
