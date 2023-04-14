package com.prueba_back.prueba_java.Service;

import com.prueba_back.prueba_java.Dto.CarDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;

public interface ServiceCars {

    ResponseGeneric save(CarDto car);

    ResponseGeneric listAll();

    ResponseGeneric listByUser(Long idUser);
}
