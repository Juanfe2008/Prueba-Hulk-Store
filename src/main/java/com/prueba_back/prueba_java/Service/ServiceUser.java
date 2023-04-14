package com.prueba_back.prueba_java.Service;

import com.prueba_back.prueba_java.Dto.UserDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;


public interface ServiceUser {

    public ResponseGeneric save(UserDto userDto);

    public ResponseGeneric listAll();

    public UserDto listById(Long id);

    public ResponseGeneric update( UserDto userDto);

}
