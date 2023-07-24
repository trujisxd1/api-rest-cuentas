package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")

public class CuntaController {

    @Autowired
    private CuentaService service;



    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCunetas(){

        List<Cuenta> cuentas = service.listar();

        if(cuentas.isEmpty()){

            return ResponseEntity.noContent().build();
        }

        return  new ResponseEntity<>(cuentas, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public  ResponseEntity<Object>listarCuenta(@PathVariable("id") Integer id){

        try{

            Cuenta cuenta = service.get(id);
            return  new ResponseEntity<>(cuenta, HttpStatus.OK);
        }catch (Exception exception){
            return  ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Object> guardar(@RequestBody Cuenta cuenta){

        Cuenta cuentaBBDD = service.save(cuenta);

        return  new ResponseEntity<>(cuenta, HttpStatus.CREATED);



    }

    @PutMapping
    public ResponseEntity<Object> editarCuenta(@RequestBody Cuenta cuenta){

        Cuenta cuentaBBDD = service.save(cuenta);

        return  new ResponseEntity<>(cuentaBBDD, HttpStatus.OK);



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarcuenta(@PathVariable Integer id){

        try {

            service.delete(id);
            return  ResponseEntity.noContent().build();

        }catch (Exception exception){

            return ResponseEntity.notFound().build();
        }



    }


}
