package tasks.manager.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks.manager.api.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByAlias(String alias);
}
