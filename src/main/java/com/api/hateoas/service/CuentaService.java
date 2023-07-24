package com.api.hateoas.service;



import com.api.hateoas.model.Cuenta;
import com.api.hateoas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Transactional
public class CuentaService {

    @Autowired
    private CuentaRepository repository;

    public List<Cuenta> listar(){

        return  this.repository.findAll();
    }

    public  Cuenta get(Integer id ){
        return this.repository.findById(id).get();



    }

    public Cuenta save (Cuenta cuenta){


        return this.repository.save(cuenta);
    }


    public  void delete(Integer id){


         this.repository.deleteById(id);
    }


}
