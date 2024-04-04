package be.ocrapi.model;

import be.ocrapi.request.OrderRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

    @Column(name = "payment_type")
    private int payment_type;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Transaction> transactions;
}
