package be.ocrapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AttendanceRequest {
    private String check_in;
    private String check_out;
    private String type;
    private String status;
    private Integer user_id;
    private String email;
    private String full_name;
    private Date updated_at = new Date();
}
