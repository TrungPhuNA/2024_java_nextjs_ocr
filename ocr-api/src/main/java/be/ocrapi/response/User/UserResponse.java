package be.ocrapi.response.User;

import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.EmployerType.TypeResponse;
import be.ocrapi.response.Rank.RankResponse;
import be.ocrapi.response.Room.RoomResponse;
import be.ocrapi.response.Salary.SalaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse {
    private Integer id;
    private String name;
    private String status;
    private String email;
    private String code;
    private String gender;
    private String phone;
    private String avatar;
    private String userType;
    private String address;
    private String cccd;
    private String cccdAddress;
    private Date cccdDate;
    private String region;
    private Date dob;

    private TypeResponse employerType;

    private CertificateResponse certificate;

    private SalaryResponse salary;

    private RoomResponse room;

    private RankResponse rank;
}
