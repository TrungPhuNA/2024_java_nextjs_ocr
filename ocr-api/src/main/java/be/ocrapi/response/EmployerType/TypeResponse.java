package be.ocrapi.response.EmployerType;

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
public class TypeResponse {
    private Integer id;
    private String name ;
    private String status;
    private String description;
    private Date created_at;
    private Date updated_at;
    private UserRelationResponse user;



}
