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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CarsImpl implements ServiceCars {

    @Autowired
    CarsRepository carRepository;

    @Autowired
    KardexRepository kardexRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseGeneric save(CarDto car) {
        try{
            Cars cars1 = new Cars();
            if (car.idProduct() != null){
                Products products = productRepository.findByIdProduc(car.idProduct());
                if (products != null){
                    if (car.cantidad() > products.getQuantityProduct()){
                        return ResponseGeneric
                                .builder()
                                .codResponse(400)
                                .status("Bad Request")
                                .message("No se pueden vender mas productos de los que existen, solo quedan: "+products.getQuantityProduct())
                                .build();
                    }
                    if (car.cantidad() < products.getQuantityProduct()){
                        /*productRepository.deleteById(products.getId());*/
                        Products products1 = new Products();

                        Long newQuantity = products.getQuantityProduct() - car.cantidad();

                        products1.setId(products.getId());
                        products1.setNameProduct(products.getNameProduct());
                        products1.setValueProduct(products.getValueProduct());
                        products1.setWeightProduct(products.getWeightProduct());
                        products1.setQuantityProduct(newQuantity);
                        productRepository.save(products1);


                        /* Kardex*/
                        SystemKardex systemKardex = new SystemKardex();

                        Float Value = products.getValueProduct() * car.cantidad();

                        systemKardex.setName(products.getNameProduct());
                        systemKardex.setUnit(products.getUnitProduct());
                        systemKardex.setSupplier(products.getSupplier());
                        systemKardex.setDate(new Date());
                        systemKardex.setValue(products.getValueProduct());
                        systemKardex.setQuantityDepartures(car.cantidad());
                        systemKardex.setValueDepartures(Value);
                        systemKardex.setQuantityBalance(products.getQuantityProduct());
                        systemKardex.setValueBalance(Value);
                        kardexRepository.save(systemKardex);
                    }
                }
            }

            Products products = productRepository.findByIdProduc(car.idProduct());
            Users users = userRepository.findByIdUsers(car.idUser());

            if(car != null){
                cars1.setQuantity(car.cantidad());
                cars1.setIdProduct(products);
                cars1.setIdUser(users);
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
