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
public class BonusAndDisciplineRequest {
    private String name;
    private String content;
    private String type;
    private Float data_value;
    private String status;
    private Integer user_id;
    private Integer updated_by;
    private Date updated_at = new Date();
}
