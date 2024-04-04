package be.ocrapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionRequest {
    private Integer id;

    private String name;

    private Integer order_id;

    private Integer status;

    private Integer discount;

    private Integer price;

    private Integer quantity;

    private Integer total_price;
}
