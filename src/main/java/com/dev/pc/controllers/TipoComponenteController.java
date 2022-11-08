package com.dev.pc.controllers;

import com.dev.pc.models.TipoComponente;
import com.dev.pc.services.TipoComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class TipoComponenteController {

    @Autowired
    private TipoComponenteService service;

    @GetMapping("/tipo-componentes")
    public List<TipoComponente> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/tipo-componentes/{id}")
    public ResponseEntity<TipoComponente> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            TipoComponente tc = this.service.obtener(id);

            return new ResponseEntity<TipoComponente>(tc, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoComponente>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tipo-componentes")
    public ResponseEntity<TipoComponente> add( @RequestBody TipoComponente tipo) throws Exception {

        try {

            TipoComponente tp = service.registrar(tipo);

            return new ResponseEntity<TipoComponente>(tp, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<TipoComponente>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/tipo-componentes/{id}")
    public ResponseEntity<TipoComponente> update( @RequestBody TipoComponente tp ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(tp);

            return new ResponseEntity<TipoComponente>(tp,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<TipoComponente>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/tipo-componentes/{id}")
    public ResponseEntity<TipoComponente> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<TipoComponente>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoComponente>(HttpStatus.NOT_FOUND);
        }
    }

}
