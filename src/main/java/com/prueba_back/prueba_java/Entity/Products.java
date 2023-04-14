package com.prueba_back.prueba_java.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "IDProducts")
    private Long id;

    @Column(name = "Name", length = 255)
    private String nameProduct;

    @Column(name = "Value")
    private Float valueProduct;

    @Column(name = "Weight", length = 255)
    private String weightProduct;

    @Column(name = "Quantity")
    private Long quantityProduct;

    @Column(name = "Unit")
    private String unitProduct;

    @Column(name = "Supplier")
    private String supplier;

	

}
