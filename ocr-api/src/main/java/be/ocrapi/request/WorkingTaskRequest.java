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
public class WorkingTaskRequest {
    private String name;
    private String description;
    private Integer user_id;
    private String status;
    private String bonus;
    private String from_date;
    private String to_date;
    private Date updated_at = new Date();
}
