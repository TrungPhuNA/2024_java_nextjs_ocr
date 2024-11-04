package be.ocrapi.response.Attendance;

import be.ocrapi.response.User.UserRelationResponse;
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
public class AttendanceResponse {
    private Integer id;
    private Date check_out;
    private String email;
    private String full_name;
    private Integer user_id;
    private Date check_in;
    private String status;
    private String type;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;
}
