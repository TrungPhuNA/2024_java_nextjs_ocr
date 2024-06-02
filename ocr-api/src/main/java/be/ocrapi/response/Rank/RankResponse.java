package be.ocrapi.response.Rank;

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
public class RankResponse {
    private Integer id;
    private String name;
    private String code;
    private Double salary;
    private Integer user_id;
    private String status;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;
}
