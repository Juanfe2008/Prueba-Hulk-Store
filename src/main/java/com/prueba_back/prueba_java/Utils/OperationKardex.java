package com.prueba_back.prueba_java.Utils;

import com.prueba_back.prueba_java.Dto.CarDto;
import com.prueba_back.prueba_java.Dto.ProductDto;
import com.prueba_back.prueba_java.Entity.Products;
import com.prueba_back.prueba_java.Entity.SystemKardex;
import com.prueba_back.prueba_java.Repository.KardexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OperationKardex {


    private final KardexRepository kardexRepository;

    public void saveKardex(ProductDto productDto){
        try {
            SystemKardex systemKardex = new SystemKardex();

            var ValueTickets = productDto.getValueProduct() * productDto.getQuantityProduct();

            systemKardex.setName(productDto.getNameProduct());
            systemKardex.setUnit(productDto.getUnitProduct());
            systemKardex.setSupplier(productDto.getSupplier());
            systemKardex.setDate(new Date());
            systemKardex.setValue(productDto.getValueProduct());
            systemKardex.setQuantityTickets(productDto.getQuantityProduct());
            systemKardex.setValueTickets(ValueTickets);
            systemKardex.setQuantityBalance(productDto.getQuantityProduct());
            systemKardex.setValueBalance(ValueTickets);
            kardexRepository.save(systemKardex);
            System.out.println("Guardado exitosamente!");
        }catch (Exception e){
            System.out.println("Error al guardado en kardex"+ e.getMessage());
        }
    }

    public void exitKardex(CarDto carDto, Products products){
        SystemKardex systemKardex = new SystemKardex();
        var listKardex = kardexRepository.orderKardex(products.getNameProduct());
        Long contador = 0L;
        for (SystemKardex kardex : listKardex){
           contador =  carDto.getQuantity() - kardex.getQuantityTickets();
            if (contador > 0 ){
                var updateKardex = kardexRepository.findByIdSystem(kardex.getId());
                updateKardex.setQuantityTickets(null);
                kardexRepository.save(updateKardex);
                System.out.println("Positivo "+systemKardex);
            }
            if (contador < 0){
                var updateKardex = kardexRepository.findByIdSystem(kardex.getId());
                updateKardex.setQuantityTickets(Math.abs(contador));
                kardexRepository.save(updateKardex);
                System.out.println("Negativo "+systemKardex);
                break;
            }
            if (contador == 0){
                var updateKardex = kardexRepository.findByIdSystem(kardex.getId());
                updateKardex.setQuantityTickets(null);
                kardexRepository.save(updateKardex);
                System.out.println("Igual "+systemKardex);
                break;
            }
        }

        var Value = products.getValueProduct() * carDto.getQuantity();
        var ValueCount = products.getValueProduct() * Math.abs(contador) ;

        systemKardex.setName(products.getNameProduct());
        systemKardex.setUnit(products.getUnitProduct());
        systemKardex.setSupplier(products.getSupplier());
        systemKardex.setDate(new Date());
        systemKardex.setValue(products.getValueProduct());
        systemKardex.setQuantityDepartures(carDto.getQuantity());
        systemKardex.setValueDepartures(Value);
        systemKardex.setQuantityTickets(Math.abs(contador));
        systemKardex.setValueTickets(ValueCount);
        kardexRepository.save(systemKardex);
    }
}
