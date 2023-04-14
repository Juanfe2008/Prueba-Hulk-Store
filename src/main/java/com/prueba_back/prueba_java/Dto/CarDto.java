package com.prueba_back.prueba_java.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    @JsonProperty("idUser")
    private Long idUser;

    @JsonProperty("idProduct")
    private Long idProduct;

    @JsonProperty("quantity")
    private Long quantity;

    @JsonProperty("total")
    private Long total;

	

}
