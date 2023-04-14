package com.prueba_back.prueba_java.Service.Impl;

import com.prueba_back.prueba_java.Dto.UserDto;
import com.prueba_back.prueba_java.Entity.Users;
import com.prueba_back.prueba_java.Repository.UserRepository;
import com.prueba_back.prueba_java.Response.ResponseGeneric;
import com.prueba_back.prueba_java.Service.ServiceUser;
import com.prueba_back.prueba_java.Utils.EncriptarDesencriptar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ImplUser implements ServiceUser {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseGeneric save(UserDto userDto) {

        try{

            String PasswordEncryp = EncriptarDesencriptar.Encriptar(userDto.getPassword());
            String UserNameEncryp = EncriptarDesencriptar.Encriptar(userDto.getUsername());



            if(userDto != null){
                ModelMapper mapper = new ModelMapper();
                Users users = mapper.map(userDto, Users.class);
                users.setUsername(UserNameEncryp);
                users.setPassword(PasswordEncryp);
                userRepository.save(users);
                users.setPassword("");
                return ResponseGeneric.builder().codResponse(201).status("Ok").message("Producto Guardado exitosamente").objectGeneric(users).build();
            }
            return ResponseGeneric.builder().codResponse(400).status("Bad Request").message("Error al guardar, no envio los datos").build();
        }catch (Exception e){
            return ResponseGeneric.builder().codResponse(400).status("Bad Request").message(e.toString()).build();
        }


    }

    @Override
    public ResponseGeneric listAll() {

        try{

            var users =  userRepository.findAll();
            for (Users user : users) {
                user.setUsername(EncriptarDesencriptar.Desencriptar(user.getUsername()));
            }
            return ResponseGeneric.builder().codResponse(200).status("Ok").message("Listado exitosamente").listObjectGeneric(Collections.singletonList(users)).build();
        }catch (Exception e){
            return ResponseGeneric
                    .builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("Bad Request")
                    .build();
        }

    }

    @Override
    public UserDto listById(Long id) {
        return null;
    }

    @Override
    public ResponseGeneric update( UserDto userDto) {
        try{

            var users = this.userRepository.findByIdUsers(userDto.getId());
            users.setId(userDto.getId());
            this.userRepository.save(users);
            return ResponseGeneric.builder().codResponse(201).status("Ok").message("Usuario actualizado exitosamente").objectGeneric(users).build();

        }catch (Exception e){
            return ResponseGeneric.builder()
                    .codResponse(400)
                    .message(e.toString())
                    .status("400")
                    .build();
        }

    }
}
