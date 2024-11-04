package be.ocrapi.response.Attendance;

import be.ocrapi.response.Bonus.BonusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ListAttendanceResponse {
    private List<AttendanceResponse> data;
    private Long total;
}
