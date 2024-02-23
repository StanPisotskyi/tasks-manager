package tasks.manager.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.entities.enums.Role;
import tasks.manager.api.requests.AccountEditRequest;
import tasks.manager.api.requests.LoginRequest;
import tasks.manager.api.requests.PasswordRequest;
import tasks.manager.api.requests.RegisterRequest;
import tasks.manager.api.responses.JwtAuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRegisterService userRegisterService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(RegisterRequest request) {

        User user = this.userRegisterService.create(request);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse update(AccountEditRequest request) {
        User user = this.userService.update(request);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}