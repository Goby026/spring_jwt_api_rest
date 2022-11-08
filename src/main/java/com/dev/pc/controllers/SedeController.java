package com.dev.pc.controllers;

import com.dev.pc.models.Sede;
import com.dev.pc.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class SedeController {

    @Autowired
    private SedeService service;

    @GetMapping("/sedes")
    public List<Sede> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/sedes/{id}")
    public ResponseEntity<Sede> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Sede sede = service.obtener(id);

            return new ResponseEntity<Sede>(sede, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sede>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sedes")
    public ResponseEntity<Sede> add( @RequestBody Sede req_sede) throws Exception {

        try {

            Sede sede = service.registrar(req_sede);

            return new ResponseEntity<Sede>(sede, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Sede>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/sedes/{id}")
    public ResponseEntity<Sede> update( @RequestBody Sede req_sede ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(req_sede);

            return new ResponseEntity<Sede>(req_sede,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Sede>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/sedes/{id}")
    public ResponseEntity<Sede> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Sede>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sede>(HttpStatus.NOT_FOUND);
        }
    }

}
