package edu.bbte.idde.csim2126.springbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuInDto implements Serializable {
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 8192)
    private String description;

    @NotNull
    @Size(max = 255)
    private String ingredients;

    @NotNull
    @Size(max = 255)
    private String category;

    @NotNull
    @Positive
    private Double price;

}
