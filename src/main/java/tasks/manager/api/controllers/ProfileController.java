package tasks.manager.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tasks.manager.api.entities.User;
import tasks.manager.api.records.UserRecord;
import tasks.manager.api.security.UserService;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/me")
    public UserRecord me() {
        User user = this.userService.getCurrentUser();
        return new UserRecord(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
    }
}
