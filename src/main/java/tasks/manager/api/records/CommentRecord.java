package tasks.manager.api.records;

import java.util.Date;
import java.util.List;

public record CommentRecord(Long id, String text, Date createdAt, Date updatedAt, UserRecord createdBy, Long replyId, List<CommentRecord> children) {
}
