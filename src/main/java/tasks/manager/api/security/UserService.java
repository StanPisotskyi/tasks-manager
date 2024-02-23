package tasks.manager.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.entities.enums.Role;
import tasks.manager.api.repositories.UserRepository;
import tasks.manager.api.requests.AccountEditRequest;
import tasks.manager.api.requests.UserEditRequest;
import tasks.manager.api.specifications.UserSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User with such username already exists");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with such email already exists");
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public User findOneById(Long id) {
        if (!this.repository.existsById(id)) {
            throw new RuntimeException(STR."User with id[\{id}] is not found");
        }

        Optional<User> findByRes = this.repository.findById(id);

        ArrayList<User> res = new ArrayList<>();
        findByRes.ifPresent(res::add);

        return res.getFirst();
    }

    public List<User> getAll() {
        return this.repository.findAll();
    }

    public User update(AccountEditRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with such username already exists");
        }

        User user = this.getCurrentUser();

        BeanUtils.copyProperties(request, this.getCurrentUser(), "id", "email", "password", "role");

        return save(user);
    }

    public User update(UserEditRequest request, User user) {
        if (!Objects.equals(user.getUsername(), request.getUsername()) && repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with such username already exists");
        }

        if (!Objects.equals(user.getEmail(), request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with such email already exists");
        }

        BeanUtils.copyProperties(request, user, "id", "password");

        return save(user);
    }

    public void delete(User user) {
        Specification<User> filters = Specification.where(UserSpecification.checkIsAdmin());

        long count = this.repository.count(filters);

        if (count == 1) {
            throw new RuntimeException("There is only one admin left");
        }

        this.repository.deleteById(user.getId());
    }
}
