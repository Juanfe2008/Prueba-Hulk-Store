package com.prueba_back.prueba_java.Utils;

import com.prueba_back.prueba_java.Dto.ProductDto;
import com.prueba_back.prueba_java.Entity.SystemKardex;
import com.prueba_back.prueba_java.Repository.KardexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class OperationKardex {


    private final KardexRepository kardexRepository;

    public void saveKardex(ProductDto productDto){
        try {
            SystemKardex systemKardex = new SystemKardex();

            var ValueTickets = productDto.value() * productDto.quantity();

            systemKardex.setName(productDto.name());
            systemKardex.setUnit(productDto.unit());
            systemKardex.setSupplier(productDto.supplier());
            systemKardex.setDate(new Date());
            systemKardex.setValue(productDto.value());
            systemKardex.setQuantityTickets(productDto.quantity());
            systemKardex.setValueTickets(ValueTickets);
            systemKardex.setQuantityBalance(productDto.quantity());
            systemKardex.setValueBalance(ValueTickets);
            kardexRepository.save(systemKardex);
            System.out.println("Guardado exitosamente!");
        }catch (Exception e){
            System.out.println("Error al guardado en kardex"+ e.getMessage());
        }
    }
}
