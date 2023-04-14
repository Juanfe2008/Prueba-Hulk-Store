package com.prueba_back.prueba_java.Service.Impl;

import com.prueba_back.prueba_java.Dto.ProductDto;
import com.prueba_back.prueba_java.Entity.Products;
import com.prueba_back.prueba_java.Repository.ProductRepository;
import com.prueba_back.prueba_java.Response.ResponseGeneric;
import com.prueba_back.prueba_java.Service.ServiceProduct;
import com.prueba_back.prueba_java.Utils.OperationKardex;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImplProduct implements ServiceProduct {


    private final ProductRepository productRepository;


    private final OperationKardex operationKardex;



    @Override
    public ResponseGeneric save(ProductDto productDto) {
        try {

            if (Objects.isNull(productDto) || productDto.name().isEmpty()){
                return ResponseGeneric
                        .builder()
                        .codResponse(400)
                        .status("Ok")
                        .message("Error al registrar el producto")
                        .build();
            }

            /* Kardex*/
            operationKardex.saveKardex(productDto);

            var mapper = new ModelMapper();
            var products = mapper.map(productDto, Products.class);
            productRepository.save(products);

            return ResponseGeneric
                    .builder()
                    .codResponse(201)
                    .status("Ok")
                    .message("Producto actualizado exitosamente")
                    .objectGeneric(products)
                    .build();

        }catch (Exception e){
            return ResponseGeneric.builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("400")
                    .build();
        }


    }

    @Override
    public ResponseGeneric listAll() {
            try{
                var productDto =  productRepository.findAll();
                if (productDto.isEmpty()){
                    return ResponseGeneric
                            .builder()
                            .codResponse(204)
                            .status("Not Content")
                            .message("No hay productos registrados")
                            .build();

                }
                return ResponseGeneric
                        .builder()
                        .codResponse(200)
                        .status("Ok")
                        .message("Producto listado exitosamente")
                        .listObjectGeneric(Collections.singletonList(productDto))
                        .build();

            }catch (Exception e){
                return ResponseGeneric.builder()
                        .codResponse(400)
                        .message(e.toString())
                        .status("400")
                        .build();
            }



    }

    @Override
    public ResponseGeneric listById(Long id) {

        try{

            if (id == null){
                return ResponseGeneric
                        .builder()
                        .codResponse(400)
                        .status("Bad Request")
                        .message("Error al consultar el producto")
                        .build();
            }
            var products = productRepository.findByIdProduc(id);
            if (Objects.isNull(products)){
                return ResponseGeneric
                        .builder()
                        .codResponse(204)
                        .status("Not Content")
                        .message("No hay productos registrados")
                        .build();
            }

            return ResponseGeneric
                    .builder()
                    .codResponse(201)
                    .status("Ok")
                    .message("Producto consultado exitosamente")
                    .objectGeneric(products)
                    .build();


        }catch (Exception e){
            return ResponseGeneric.builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("400")
                    .build();
        }
    }

    @Override
    public ResponseGeneric update(Long id, ProductDto productDto) {
        try {
            if (Objects.isNull(productDto) || id == null) {
                return ResponseGeneric
                        .builder()
                        .codResponse(400)
                        .status("Bad Request")
                        .message("Error al actualizar el producto")
                        .build();
            }
            /* Kardex*/
            operationKardex.saveKardex(productDto);

            var productsUpdate = productRepository.findByIdProduc(productDto.id());
            if (Objects.isNull(productsUpdate)){
                return ResponseGeneric
                        .builder()
                        .codResponse(400)
                        .status("Bad Request")
                        .message("No se encontro el producto a actualizar")
                        .build();
            }

            var NewQuantity = productDto.quantity() + productsUpdate.getQuantityProduct();
            var mapper = new ModelMapper();
            var products = mapper.map(productDto, Products.class);
            products.setQuantityProduct(NewQuantity);
            productRepository.save(products);
            return ResponseGeneric
                    .builder()
                    .codResponse(201)
                    .status("Ok")
                    .message("Producto actualizado exitosamente")
                    .objectGeneric(products)
                    .build();
        } catch (Exception e){
            return ResponseGeneric.builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("Bad request")
                    .build();
        }
    }

    @Override
    public ResponseGeneric deleteById(Long id) {
        try{
            if(id == null){
                return ResponseGeneric.builder()
                        .codResponse(400)
                        .status("Bad Request")
                        .message("No se puede eliminar el producto, no se envio un ID")
                        .build();
            }
            productRepository.deleteById(id);
            return ResponseGeneric.builder().codResponse(200).status("Ok").message("Producto eliminado correctamente").build();
        }catch (Exception e){
            return ResponseGeneric.builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("400")
                    .build();
        }

    }
}
