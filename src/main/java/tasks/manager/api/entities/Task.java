package tasks.manager.api.entities;

import jakarta.persistence.*;
import lombok.*;
import tasks.manager.api.entities.enums.TaskStatus;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition="text")
    private String description;

    @Column(name = "created_at", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="assignet_to", referencedColumnName = "id")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id", nullable = false)
    private Project project;
}
