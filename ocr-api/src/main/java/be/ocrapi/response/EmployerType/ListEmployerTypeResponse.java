package be.ocrapi.response.EmployerType;

import be.ocrapi.response.Room.RoomResponse;
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
public class ListEmployerTypeResponse {
    private List<TypeResponse> data;
    private Long total;
}
