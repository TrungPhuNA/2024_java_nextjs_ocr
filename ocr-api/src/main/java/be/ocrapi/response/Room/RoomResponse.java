package be.ocrapi.response.Room;

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
public class RoomResponse {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
//    private UserRelationResponse user;
}
