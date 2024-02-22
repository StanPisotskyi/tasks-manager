package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.User;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.requests.PasswordRequest;
import tasks.manager.api.requests.UserEditRequest;
import tasks.manager.api.security.AuthenticationService;
import tasks.manager.api.security.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserRecordFactory userRecordFactory;
    private final AuthenticationService authenticationService;

    @GetMapping("")
    public List<UserRecord> getAll() {
        return this.userRecordFactory.create(this.userService.getAll());
    }

    @GetMapping("/{id}")
    public UserRecord getOne(@PathVariable("id") User user) {
        return this.userRecordFactory.create(user);
    }

    @PutMapping("/{id}/account")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRecord update(@PathVariable("id") User user, @RequestBody @Valid UserEditRequest request) {
        return this.userRecordFactory.create(this.userService.update(request, user));
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public DefaultRecord password(@PathVariable("id") User user, @RequestBody @Valid PasswordRequest request) {
        this.authenticationService.updatePassword(user, request);

        return new DefaultRecord(true, "Password has been changed");
    }
}
