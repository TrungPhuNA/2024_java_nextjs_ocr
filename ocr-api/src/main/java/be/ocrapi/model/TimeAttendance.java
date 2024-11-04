package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Table(name = "time_attendance")
@Getter @Setter
public class TimeAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name", nullable = true)
    private String full_name;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "check_in", nullable = true)
    private Date check_in;

    @Column(name = "check_out", nullable = true)
    private Date check_out;

    @Column(name = "type", nullable = true)
    @Comment("MORNING: Ca sáng --- AFTERNOON: Chiều")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", nullable = true)
    private Date created_at;

    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
