package tasks.manager.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.entities.Task;
import tasks.manager.api.repositories.CommentRepository;
import tasks.manager.api.requests.AnswerRequest;
import tasks.manager.api.requests.CommentEditRequest;
import tasks.manager.api.requests.CommentRequest;
import tasks.manager.api.security.UserService;
import tasks.manager.api.specifications.CommentSpecification;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    public static Map<Long, List<Comment>> commentsByParents = new HashMap<>();

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
            throw new RuntimeException("Comment with id[" + id + "] is not found");
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
            throw new RuntimeException("Comment with id[" + comment.getId() + "] is not found");
        }

        if (!this.hasAccess(comment)) {
            throw new RuntimeException("Access denied");
        }

        this.commentRepository.deleteById(comment.getId());
    }

    public List<Comment> getListByTask(Task task) {
        commentsByParents.clear();

        Specification<Comment> filters = Specification
                .where(CommentSpecification.setTask(task));

        List<Comment> comments = this.commentRepository.findAll(filters);
        List<Comment> commentsToRemove = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getReply() == null) {
                continue;
            }

            Long replyId = comment.getReply().getId();

            if (!commentsByParents.containsKey(replyId)) {
                commentsByParents.put(comment.getReply().getId(), new ArrayList<>());
            }

            List<Comment> children = commentsByParents.get(replyId);
            children.add(comment);

            commentsByParents.put(comment.getReply().getId(), children);

            commentsToRemove.add(comment);
        }

        for (Comment commentToRemove : commentsToRemove) {
            comments.remove(commentToRemove);
        }

        return comments;
    }

    private boolean hasAccess(Comment comment) {
        return Objects.equals(comment.getCreatedBy().getId(), this.userService.getCurrentUser().getId());
    }
}
