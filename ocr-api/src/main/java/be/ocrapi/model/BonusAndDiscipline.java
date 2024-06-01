package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "bonus_disciplines")
@Getter @Setter
public class BonusAndDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;


    @Column(name = "created_at", nullable = true)
    private Date created_at;

    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
