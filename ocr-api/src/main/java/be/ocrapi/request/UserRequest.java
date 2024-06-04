package be.ocrapi.request;

import be.ocrapi.model.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRequest {

    private String name;
    private String status;

    private String email;
    private String code;

    private String password;

    private String gender;

    private String phone;

    private String avatar;

    private String accessToken;

    private String refreshToken;

    private String userType;
    private String address;
    private String cccd;
    private String cccdAddress;
    private String cccdDate;
    private String region;
    private String dob;

    private Integer employerTypeId;
    private Integer certificateId;
    private Integer roomId;
    private Integer userRankId;
}
