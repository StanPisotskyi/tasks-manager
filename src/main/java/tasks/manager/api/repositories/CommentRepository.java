package tasks.manager.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks.manager.api.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
