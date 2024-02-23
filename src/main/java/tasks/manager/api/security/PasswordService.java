package tasks.manager.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.requests.PasswordRequest;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void update(User user, PasswordRequest request) {
        if (!request.equals()) {
            throw new RuntimeException("Passwords are not equal");
        }

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.userService.save(user);
    }
}
