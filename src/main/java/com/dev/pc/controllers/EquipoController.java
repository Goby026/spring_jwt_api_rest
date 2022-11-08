package com.dev.pc.controllers;

import com.dev.pc.models.Equipo;
import com.dev.pc.services.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

public class EquipoController {

    @Autowired
    private EquipoService service;

    @GetMapping("/equipos")
    public List<Equipo> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipo> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Equipo e = service.obtener(id);

            return new ResponseEntity<Equipo>(e, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/equipos")
    public ResponseEntity<Equipo> add( @RequestBody Equipo e) throws Exception {

        try {

            Equipo equipo = service.registrar(e);

            return new ResponseEntity<Equipo>(equipo, HttpStatus.CREATED);

        } catch (NoSuchElementException ex) {

            return new ResponseEntity<Equipo>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/equipos/{id}")
    public ResponseEntity<Equipo> update( @RequestBody Equipo e ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(e);

            return new ResponseEntity<Equipo>(e,HttpStatus.OK);

        } catch (NoSuchElementException ex) {

            return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Equipo> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Equipo>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Equipo>(HttpStatus.NOT_FOUND);
        }
    }
}
