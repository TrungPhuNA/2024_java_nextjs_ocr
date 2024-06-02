package be.ocrapi.response.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRelationResponse {
    private Integer id;
    private String name;
    private String email;
    private String code;
    private String gender;
    private String avatar;

//    private String userType;
//    private String address;
//    private String cccd;
//    private String cccdAddress;
//    private String cccdDate;
//    private String region;
//    private String dob;
}
