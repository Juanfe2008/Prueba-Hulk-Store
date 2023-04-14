package com.prueba_back.prueba_java.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nameProduct")
    private String nameProduct;

    @JsonProperty("valueProduct")
    private Float valueProduct;

    @JsonProperty("weightProduct")
    private String weightProduct;

    @JsonProperty("quantityProduct")
    private Long quantityProduct;

    @JsonProperty("unitProduct")
    private String unitProduct;

    @JsonProperty("supplier")
    private String supplier;

	

}
