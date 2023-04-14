package com.prueba_back.prueba_java.Service;

import com.prueba_back.prueba_java.Dto.ProductDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;

public interface ServiceProduct {

    public ResponseGeneric save(ProductDto productDto);

    public ResponseGeneric listAll();

    public ResponseGeneric listById(Long id);

    public ResponseGeneric update(Long id, ProductDto productDto);

    public ResponseGeneric deleteById(Long id);

}
