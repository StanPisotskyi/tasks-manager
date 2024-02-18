package tasks.manager.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tasks.manager.api.factories.UserRecordFactory;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.security.UserService;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final UserRecordFactory userRecordFactory;

    @GetMapping("/me")
    public UserRecord me() {
        return this.userRecordFactory.create(this.userService.getCurrentUser());
    }
}
