package be.ocrapi.response;

import be.ocrapi.model.Order;
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
public class StatisticResponse {
    private List<OrderResponse> order;
    private Integer total_order;
    private Integer total_price;
    private List<OrderResponse> price ;
}
