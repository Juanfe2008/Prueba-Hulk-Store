package com.prueba_back.prueba_java.Controller;

import com.prueba_back.prueba_java.Dto.UserDto;
import com.prueba_back.prueba_java.Response.ResponseGeneric;
import com.prueba_back.prueba_java.Service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private ServiceUser serviceUser;


    @GetMapping(value = "/list")
    public ResponseEntity<ResponseGeneric> listUsers(){

        return new ResponseEntity<>(serviceUser.listAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseGeneric> usersSave(@RequestBody UserDto users){
        return new ResponseEntity<>(serviceUser.save(users),HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseGeneric> usersUpdate(@RequestBody UserDto users){
        return new ResponseEntity<>(serviceUser.update(users),HttpStatus.CREATED);
    }
}
