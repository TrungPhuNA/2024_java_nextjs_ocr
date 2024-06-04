package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter @Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private Date dob;


    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;



    @Column(name = "address")
    private String address;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "cccd_address")
    private String cccdAddress;

    @Column(name = "cccd_date")
    private Date cccdDate;

    @Column(name = "region")
    private String region;


    @Column(name = "user_type")
    private String userType;


    @ManyToOne
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private EmployerType employerType;

    @ManyToOne
    @JoinColumn(name = "certificate_id",referencedColumnName = "id")
    private Certificate certificate;

    @ManyToOne
    @JoinColumn(name = "room_id",referencedColumnName = "id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "rank_id",referencedColumnName = "id")
    private Rank rank;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
