package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "salaries")
@Getter @Setter
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "status")
    private String status;


    @Column(name = "created_at", nullable = true)
    private Date created_at;

    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
