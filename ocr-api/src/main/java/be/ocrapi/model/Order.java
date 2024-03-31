package be.ocrapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "total_discount")
    private Integer total_discount;

    @Column(name = "total_price")
    private Integer total_price;

    @Column(name = "node")
    private String node;

    @Column(name = "status")
    private Integer status;

    @Column(name = "receiver_name")
    private String receiver_name;

    @Column(name = "receiver_email")
    private String receiver_email;

    @Column(name = "receiver_phone")
    private String receiver_phone;

    @Column(name = "receiver_address")
    private String receiver_address;
}
