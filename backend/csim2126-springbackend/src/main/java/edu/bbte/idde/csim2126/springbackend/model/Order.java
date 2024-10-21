package edu.bbte.idde.csim2126.springbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "customer_date")
    private String date;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
