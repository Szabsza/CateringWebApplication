package edu.bbte.idde.csim2126.springbackend.dto;

import lombok.Data;

@Data
public class OrderOutDto {
    private Long id;
    private String customerName;
    private String deliveryAddress;
    private String date;
    private Double totalPrice;
    private Long menuId;

}
