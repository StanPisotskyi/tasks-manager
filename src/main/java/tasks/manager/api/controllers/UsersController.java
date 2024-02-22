package tasks.manager.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.security.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserRecordFactory userRecordFactory;

    @GetMapping("")
    public List<UserRecord> getAll() {
        return this.userRecordFactory.create(this.userService.getAll());
    }
}
