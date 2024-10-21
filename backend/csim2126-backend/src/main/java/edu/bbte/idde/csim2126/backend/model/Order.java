package edu.bbte.idde.csim2126.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends BaseModel {
    private String date;
    private String customerName;
    private String deliveryAddress;
    private Double totalPrice;

}
