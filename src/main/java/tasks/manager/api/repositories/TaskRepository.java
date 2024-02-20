package tasks.manager.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tasks.manager.api.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select t.* from tasks t order by t.created_at desc limit ?1 offset ?2", nativeQuery = true)
    public List<Task> getList(int limit, int offset);
}
