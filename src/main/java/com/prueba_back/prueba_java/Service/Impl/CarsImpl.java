package com.prueba_back.prueba_java.Service.Impl;

import com.prueba_back.prueba_java.Dto.CarDto;
import com.prueba_back.prueba_java.Entity.Cars;
import com.prueba_back.prueba_java.Entity.SystemKardex;
import com.prueba_back.prueba_java.Entity.Products;
import com.prueba_back.prueba_java.Entity.Users;
import com.prueba_back.prueba_java.Repository.CarsRepository;
import com.prueba_back.prueba_java.Repository.KardexRepository;
import com.prueba_back.prueba_java.Repository.ProductRepository;
import com.prueba_back.prueba_java.Repository.UserRepository;
import com.prueba_back.prueba_java.Response.ResponseGeneric;
import com.prueba_back.prueba_java.Service.ServiceCars;
import com.prueba_back.prueba_java.Utils.OperationKardex;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarsImpl implements ServiceCars {


    private final CarsRepository carRepository;


    private final KardexRepository kardexRepository;


    private final ProductRepository productRepository;


    private final UserRepository userRepository;

    private final OperationKardex operationKardex;

    @Override
    public ResponseGeneric save(CarDto car) {
        try{
            Cars cars1 = new Cars();

            if (Objects.isNull(car.getIdProduct())){
                return ResponseGeneric
                        .builder()
                        .codResponse(400)
                        .status("Bad request")
                        .message("No se envio ningun id de producto")
                        .build();
            }

            Products products = productRepository.findByIdProduc(car.getIdProduct());
            if (products != null){
                if (car.getQuantity() != null){

                    var newQuantity = products.getQuantityProduct() - car.getQuantity();
                    products.setQuantityProduct(newQuantity);
                    productRepository.save(products);
                    /* Kardex*/
                    operationKardex.exitKardex(car, products);


                }
            }

            Users users = userRepository.findByIdUsers(car.getIdUser());

            if(car != null){
                cars1.setQuantity(car.getQuantity());
                cars1.setIdProduct(products);
                cars1.setIdUser(users);
                cars1.setTotal(cars1.getTotal());
                carRepository.save(cars1);
                return ResponseGeneric
                        .builder()
                        .codResponse(201)
                        .status("Created")
                        .message("Producto agregado exitosamente al carrito")
                        .objectGeneric(users)
                        .build();

            }
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad request")
                    .message("No se a podido guardar en el carrito")
                    .build();

        }catch (Exception e){
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad Request")
                    .message(e.getMessage())
                    .build();
        }


    }

    @Override
    public ResponseGeneric listAll() {
        try {
            var cars = carRepository.findAll();
            if (cars.isEmpty()){
                ResponseGeneric
                        .builder()
                        .codResponse(204)
                        .status("Not Content")
                        .message("No se a podido listar en el carrito")
                        .build();
            }
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad request")
                    .message("No se a podido guardar en el carrito")
                    .listObjectGeneric(Collections.singletonList(cars))
                    .build();
        }catch (Exception e){
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad Request")
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseGeneric listByUser(Long idUser) {
        try {
            List<Cars> cars = carRepository.findByIdUser(idUser);
            if (cars.isEmpty()){
               return ResponseGeneric
                        .builder()
                        .codResponse(204)
                        .status("Not Content")
                        .message("No se a podido listar en el carrito por usuario")
                        .build();
            }
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad request")
                    .message("No se a podido guardar en el carrito")
                    .listObjectGeneric(Collections.singletonList(cars))
                    .build();
        }catch (Exception e){
          return  ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .status("Bad Request")
                    .message(e.getMessage())
                    .build();
        }

    }
}
