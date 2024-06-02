package be.ocrapi.response.Salary;

import be.ocrapi.model.Order;
import be.ocrapi.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StatisticResponse {
    private Integer total_order;
    private Integer total_price;
    private Long total_category;
    private List<OrderResponse> group_by_status ;
    private List<OrderResponse> group_by_day ;
}
