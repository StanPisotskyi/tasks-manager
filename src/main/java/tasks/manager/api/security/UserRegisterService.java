package tasks.manager.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.entities.enums.Role;
import tasks.manager.api.requests.RegisterRequest;
import tasks.manager.api.requests.UserCreateRequest;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public User create(RegisterRequest request) {
        Role role = Role.ROLE_USER;

        if (request instanceof UserCreateRequest) {
            role = ((UserCreateRequest) request).getRole();
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        return this.userService.create(user);
    }
}
