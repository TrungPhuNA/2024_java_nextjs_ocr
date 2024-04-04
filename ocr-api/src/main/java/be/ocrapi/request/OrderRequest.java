package be.ocrapi.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderRequest {

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