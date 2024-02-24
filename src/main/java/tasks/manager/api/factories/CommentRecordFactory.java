package tasks.manager.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.records.CommentRecord;
import tasks.manager.api.services.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentRecordFactory {
    private final UserRecordFactory userRecordFactory;

    public CommentRecord create(Comment comment) {
        List<CommentRecord> children = new ArrayList<>();

        if (CommentService.commentsByParents.containsKey(comment.getId())) {
            List<Comment> childrenByParent = CommentService.commentsByParents.get(comment.getId());

            for (Comment child : childrenByParent) {
                children.add(this.create(child));
            }
        }

        return new CommentRecord(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                this.userRecordFactory.create(comment.getCreatedBy()),
                comment.getReply() == null ? null : comment.getReply().getId(),
                children.isEmpty() ? null : children
        );
    }

    public List<CommentRecord> create(List<Comment> comments) {
        List<CommentRecord> res = new ArrayList<>();

        for (Comment comment : comments) {
            res.add(this.create(comment));
        }

        return res;
    }
}
