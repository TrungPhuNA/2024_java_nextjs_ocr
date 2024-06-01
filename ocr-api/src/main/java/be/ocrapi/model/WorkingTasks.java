package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "working_tasks")
@Getter @Setter
public class WorkingTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "bonus", nullable = true)
    private String bonus;

    @Column(name = "created_at", nullable = true)
    private Date created_at;

    @Column(name = "from_date", nullable = true)
    private Date from_date;

    @Column(name = "to_date", nullable = true)
    private Date to_date;

    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
