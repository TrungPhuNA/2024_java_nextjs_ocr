package be.ocrapi.response.Certificate;

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
public class ListCertificateResponse {
    private List<CertificateResponse> data;
    private Long total;
}
