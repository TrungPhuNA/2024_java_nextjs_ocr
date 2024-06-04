package be.ocrapi.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SalaryRequest {
    private Double salary;
    private Integer user_id;
    private Integer updated_by;
    private Double workday;
    private Integer allowance;
    private Double receive_salary;
    private String from_date;
    private String to_date;
    private String status;
    private Date updated_at = new Date();
}
