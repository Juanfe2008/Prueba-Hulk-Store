package com.prueba_back.prueba_java.Entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Car")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idCar")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Users idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProducts")
    private Products idProduct;

    @Column(name = "Quantity ")
    private Long quantity ;

    @Column(name = "total")
    private Long total;


}
