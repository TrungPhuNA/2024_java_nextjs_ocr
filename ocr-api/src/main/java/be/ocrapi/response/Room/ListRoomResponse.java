package be.ocrapi.response.Room;

import be.ocrapi.response.Salary.SalaryResponse;
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
public class ListRoomResponse {
    private List<RoomResponse> data;
    private Long total;
}
