package com.prueba_back.prueba_java.Service;

import com.prueba_back.prueba_java.Dto.ProductDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;

public interface ServiceProduct {

    ResponseGeneric save(ProductDto productDto);

    ResponseGeneric listAll();

    ResponseGeneric listById(Long id);

    ResponseGeneric update(Long id, ProductDto productDto);

    ResponseGeneric deleteById(Long id);

}
