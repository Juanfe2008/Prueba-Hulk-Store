package com.prueba_back.prueba_java.Controller;

import com.prueba_back.prueba_java.Dto.CarDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;
import com.prueba_back.prueba_java.Service.ServiceCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("car")
@CrossOrigin(origins = "*")
public class CarController {

    @Autowired
    private ServiceCars carService;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseGeneric> ListProducts()throws IOException {
        return new ResponseEntity<>(carService.listAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseGeneric> CarsSave(@RequestBody CarDto car){
        return new ResponseEntity<>(carService.save(car),HttpStatus.CREATED);
    }

    @GetMapping(value = "/listByUser/{idUser}")
    public ResponseEntity<ResponseGeneric> carsByUser(@PathVariable Long idUser){
        return new ResponseEntity<>(carService.listByUser(idUser), HttpStatus.OK);
    }

}
