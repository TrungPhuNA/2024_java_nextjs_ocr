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
    private Integer id;
    private Integer user_id;
    private Integer discount;
    private Integer total_discount;
    private Integer total_price;
    private String node;
    private Integer status;
    private String receiver_name;
    private String receiver_email;
    private String receiver_phone;
    private String receiver_address;
}
