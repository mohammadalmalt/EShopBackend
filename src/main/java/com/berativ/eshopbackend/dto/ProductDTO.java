package com.berativ.eshopbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Min(0)
    private Double price;

    @Min(0)
    private Integer stockQuantity;

    private String imageUrl;

    private String category;
}
