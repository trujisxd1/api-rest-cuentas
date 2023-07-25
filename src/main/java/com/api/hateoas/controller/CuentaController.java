package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.model.Monto;
import com.api.hateoas.service.CuentaService;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")

public class CuentaController {

    @Autowired
    private CuentaService service;



    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCunetas(){

        List<Cuenta> cuentas = service.listar();

        if(cuentas.isEmpty()){

            return ResponseEntity.noContent().build();
        }

        for (Cuenta cuenta : cuentas ){

            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));

            CollectionModel<Cuenta> modelo= CollectionModel.of(cuentas);
            modelo.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withSelfRel());

        }

        return  new ResponseEntity<>(cuentas, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public  ResponseEntity<Object>listarCuenta(@PathVariable("id") Integer id){

        try{

            Cuenta cuenta = service.get(id);
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));
            return  new ResponseEntity<>(cuenta, HttpStatus.OK);
        }catch (Exception exception){
            return  ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Object> guardar(@RequestBody Cuenta cuenta){

        Cuenta cuentaBBDD = service.save(cuenta);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withSelfRel());
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(), null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));

        return  ResponseEntity.created(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).toUri()).body(cuentaBBDD);



    }



    @PutMapping
    public ResponseEntity<Object> editarCuenta(@RequestBody Cuenta cuenta){

        Cuenta cuentaBBDD = service.save(cuenta);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withSelfRel());
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(), null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));

        return  ResponseEntity.created(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).toUri()).body(cuentaBBDD);



    }
    @PatchMapping("/{id}/deposito")

    public  ResponseEntity<Object>depositarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
        Cuenta cuentaBBDD = service.depositar(monto.getMonto(), id);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withSelfRel());
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(), null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuentaBBDD,HttpStatus.OK);

    }
    @PatchMapping("/{id}/retirar")

    public  ResponseEntity<Object>retirarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
        Cuenta cuentaBBDD = service.retirar(monto.getMonto(), id);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withSelfRel());
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(), null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBBDD.getId(), null)).withRel("retirar"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCunetas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuentaBBDD,HttpStatus.OK);

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
