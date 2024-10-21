package edu.bbte.idde.csim2126.springbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderInDto {
    @NotNull
    @Size(max = 255)
    private String customerName;

    @NotNull
    @Size(max = 255)
    private String deliveryAddress;

    @NotNull
    @Size(max = 255)
    private String date;

    @NotNull
    @Positive
    private Double totalPrice;

    @NotNull
    private Long menuId;

}
