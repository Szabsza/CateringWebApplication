package edu.bbte.idde.csim2126.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Menu extends BaseModel {
    private String name;
    private Double price;
    private String description;
    private String category;
    private String ingredients;
    private Long orderId;

}
