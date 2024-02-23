package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.User;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.records.ProjectRecord;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.requests.PasswordRequest;
import tasks.manager.api.requests.ProjectRequest;
import tasks.manager.api.requests.UserCreateRequest;
import tasks.manager.api.requests.UserEditRequest;
import tasks.manager.api.security.PasswordService;
import tasks.manager.api.security.UserRegisterService;
import tasks.manager.api.security.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserRecordFactory userRecordFactory;
    private final PasswordService passwordService;
    private final UserRegisterService userRegisterService;

    @GetMapping("")
    public List<UserRecord> getAll() {
        return this.userRecordFactory.create(this.userService.getAll());
    }

    @GetMapping("/{id}")
    public UserRecord getOne(@PathVariable("id") User user) {
        return this.userRecordFactory.create(user);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRecord create(@RequestBody @Valid UserCreateRequest request) {
        return this.userRecordFactory.create(this.userRegisterService.create(request));
    }

    @PutMapping("/{id}/account")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRecord update(@PathVariable("id") User user, @RequestBody @Valid UserEditRequest request) {
        return this.userRecordFactory.create(this.userService.update(request, user));
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public DefaultRecord password(@PathVariable("id") User user, @RequestBody @Valid PasswordRequest request) {
        this.passwordService.update(user, request);

        return new DefaultRecord(true, "Password has been changed");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DefaultRecord delete(@PathVariable("id") User user) {
        this.userService.delete(user);

        return new DefaultRecord(true, "User has been deleted");
    }
}
