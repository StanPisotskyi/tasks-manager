package tasks.manager.api.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.records.CommentRecord;

@Service
@RequiredArgsConstructor
public class CommentRecordFactory {
    private final UserRecordFactory userRecordFactory;

    public CommentRecord create(Comment comment) {
        return new CommentRecord(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                this.userRecordFactory.create(comment.getCreatedBy()),
                comment.getReply() == null ? null : comment.getReply().getId(),
                null
        );
    }
}
