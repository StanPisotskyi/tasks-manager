package tasks.manager.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks.manager.api.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
