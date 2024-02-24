package tasks.manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tasks.manager.api.entities.Comment;
import tasks.manager.api.factories.CommentRecordFactory;
import tasks.manager.api.records.CommentRecord;
import tasks.manager.api.records.DefaultRecord;
import tasks.manager.api.requests.AnswerRequest;
import tasks.manager.api.requests.CommentEditRequest;
import tasks.manager.api.requests.CommentRequest;
import tasks.manager.api.services.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final CommentRecordFactory commentRecordFactory;

    @PostMapping("")
    public CommentRecord create(@RequestBody @Valid CommentRequest request) {
        return this.commentRecordFactory.create(this.commentService.create(request));
    }

    @PostMapping("/answer")
    public CommentRecord create(@RequestBody @Valid AnswerRequest request) {
        return this.commentRecordFactory.create(this.commentService.create(request));
    }

    @PutMapping("/{id}")
    public CommentRecord update(@PathVariable("id") Comment comment, @RequestBody @Valid CommentEditRequest request) {
        return this.commentRecordFactory.create(this.commentService.update(comment, request));
    }

    @DeleteMapping("/{id}")
    public DefaultRecord delete(@PathVariable("id") Comment comment) {
        this.commentService.delete(comment);

        return new DefaultRecord(true, "Comment has been deleted");
    }
}
