package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.repositories.CommentRepository;
import tasks.manager.api.requests.AnswerRequest;
import tasks.manager.api.requests.CommentEditRequest;
import tasks.manager.api.requests.CommentRequest;
import tasks.manager.api.security.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public Comment create(CommentRequest request) {
        Comment comment = Comment.builder()
                .text(request.getText())
                .createdAt(new Date())
                .createdBy(this.userService.getCurrentUser())
                .task(this.taskService.findOneById(request.getTaskId()))
                .build();

        return save(comment);
    }

    public Comment create(AnswerRequest request) {
        Comment reply = this.findOneById(request.getReplyId());

        Comment comment = Comment.builder()
                .text(request.getText())
                .createdAt(new Date())
                .createdBy(this.userService.getCurrentUser())
                .reply(reply)
                .task(reply.getTask())
                .build();

        return save(comment);
    }

    public Comment findOneById(Long id) {
        if (!this.commentRepository.existsById(id)) {
            throw new RuntimeException(STR."Comment with id[\{id}] is not found");
        }

        Optional<Comment> findByRes = this.commentRepository.findById(id);

        ArrayList<Comment> res = new ArrayList<>();
        findByRes.ifPresent(res::add);

        return res.getFirst();
    }

    public Comment update(Comment comment, CommentEditRequest request) {
        if (!this.hasAccess(comment)) {
            throw new RuntimeException("Access denied");
        }

        comment.setUpdatedAt(new Date());

        BeanUtils.copyProperties(request, comment, "id", "createdBy", "createdAt", "updatedAt", "reply", "task");

        return save(comment);
    }

    public void delete (Comment comment) {
        if (!this.commentRepository.existsById(comment.getId())) {
            throw new RuntimeException(STR."Comment with id[\{comment.getId()}] is not found");
        }

        if (!this.hasAccess(comment)) {
            throw new RuntimeException("Access denied");
        }

        this.commentRepository.deleteById(comment.getId());
    }

    private boolean hasAccess(Comment comment) {
        return Objects.equals(comment.getCreatedBy().getId(), this.userService.getCurrentUser().getId());
    }
}
