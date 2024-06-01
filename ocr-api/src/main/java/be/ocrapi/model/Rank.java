package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ranks")
@Getter @Setter
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "status")
    private String status;

    @Column(name = "salary", nullable = true)
    private Double salary;

    @Column(name = "created_at", nullable = true)
    private Date created_at;

    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
