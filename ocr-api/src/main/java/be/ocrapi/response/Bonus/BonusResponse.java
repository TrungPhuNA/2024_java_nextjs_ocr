package be.ocrapi.response.Bonus;

import be.ocrapi.response.Salary.SalaryResponse;
import be.ocrapi.response.User.UserRelationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BonusResponse {
    private Integer id;
    private String name;
    private String content;
    private Integer user_id;
    private Float data_value;
    private String status;
    private String type;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;
    private UserRelationResponse updated_by;
}
