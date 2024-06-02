package be.ocrapi.response.Salary;

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
public class SalaryResponse {
    private Integer id;
    private Double salary;
    private Integer user_id;
    private String status;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;
}
