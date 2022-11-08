package com.dev.pc.controllers;

import com.dev.pc.models.Componente;
import com.dev.pc.services.ComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class ComponenteController {

    @Autowired
    private ComponenteService service;

    @GetMapping("/componentes")
    public List<Componente> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/componentes/{id}")
    public ResponseEntity<Componente> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Componente m = service.obtener(id);

            return new ResponseEntity<Componente>(m, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Componente>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/componentes")
    public ResponseEntity<Componente> add( @RequestBody Componente c) throws Exception {

        try {

            Componente componente = service.registrar(c);

            return new ResponseEntity<Componente>(componente, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Componente>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/componentes/{id}")
    public ResponseEntity<Componente> update( @RequestBody Componente c ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(c);

            return new ResponseEntity<Componente>(c,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Componente>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/componentes/{id}")
    public ResponseEntity<Componente> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Componente>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Componente>(HttpStatus.NOT_FOUND);
        }
    }

}
