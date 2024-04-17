package be.ocrapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderRequest {

    private Integer user_id;
    private Integer total_discount;
    private Integer total_price;
    private Integer payment_type;
    private Integer category_id;
    private String code;
    private String name;
    private String image;
    private String node;
    private Integer status;
    private String receiver_name;
    private String receiver_email;
    private String receiver_phone;
    private String receiver_address;
    private Date updated_at = new Date();

    private List<TransactionRequest> transactions;
}