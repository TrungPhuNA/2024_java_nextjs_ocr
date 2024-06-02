package be.ocrapi.response.WorkingTask;

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
public class WorkingTaskResponse {
    private Integer id;
    private String bonus;
    private Integer user_id;
    private String status;
    private String description;
    private String name;
    private Date from_date;
    private Date to_date;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;
}
