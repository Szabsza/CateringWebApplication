package edu.bbte.idde.csim2126.springbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "ingredients")
    private String ingredients;

}
