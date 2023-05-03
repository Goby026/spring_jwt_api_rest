package com.dev.pc.controllers;

import com.dev.pc.models.TipoEgreso;
import com.dev.pc.services.TipoEgresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class TipoEgresoController {
    
    @Autowired
    private TipoEgresoService service;

    @GetMapping("/tipoegresos")
    public ResponseEntity<HashMap<String, List<TipoEgreso>>> list() throws Exception {
        List<TipoEgreso> tipoEgresos = this.service.listar();
        HashMap<String, List<TipoEgreso>> resp = new HashMap<>();
        resp.put("tipoEgresos", tipoEgresos);
        return new ResponseEntity<HashMap<String, List<TipoEgreso>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tipoegresos/{id}")
    public ResponseEntity<TipoEgreso> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            TipoEgreso tipoEgreso = service.obtener(id);

            return new ResponseEntity<TipoEgreso>(tipoEgreso, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoEgreso>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tipoegresos")
    public ResponseEntity<TipoEgreso> add(@RequestBody TipoEgreso te) throws Exception {
        try {
            TipoEgreso tipoEgreso = service.registrar(te);
            return new ResponseEntity<TipoEgreso>(tipoEgreso, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoEgreso>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/tipoegresos/{id}")
    public ResponseEntity<TipoEgreso> update(@RequestBody TipoEgreso te, @PathVariable Long id) throws Exception {
        try {
            TipoEgreso tipoEgreso = service.registrar(te);
            return new ResponseEntity<TipoEgreso>(tipoEgreso, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<TipoEgreso>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tipoegresos/{id}")
    public ResponseEntity<TipoEgreso> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<TipoEgreso>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoEgreso>(HttpStatus.NOT_FOUND);
        }
    }
}
