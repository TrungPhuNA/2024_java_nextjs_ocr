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
public class RankRequest {
    private String name;
    private Double salary;
    private Integer user_id;
    private String status;

    private Date updated_at = new Date();
}
