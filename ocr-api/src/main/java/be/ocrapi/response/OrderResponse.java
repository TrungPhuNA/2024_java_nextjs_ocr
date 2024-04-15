package be.ocrapi.response;

import be.ocrapi.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderResponse {
    private Integer status;
    private Integer total;
    private Integer price;
    private String date;
}
