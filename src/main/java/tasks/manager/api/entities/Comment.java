package tasks.manager.api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "text", nullable = false, columnDefinition="text")
    private String text;

    @Column(name = "created_at", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="reply_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Comment reply;

    @ManyToOne
    @JoinColumn(name="task_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply")
    private List<Comment> answers;
}
