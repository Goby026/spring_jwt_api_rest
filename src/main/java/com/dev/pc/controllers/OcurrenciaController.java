package com.dev.pc.controllers;

import com.dev.pc.models.Ocurrencia;
import com.dev.pc.services.OcurrenciaService;
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
public class OcurrenciaController {
    
    @Autowired
    private OcurrenciaService service;

    @GetMapping("/ocurrencias")
    public ResponseEntity<HashMap<String, List<Ocurrencia>>> list() throws Exception {
        List<Ocurrencia> ocurrencias = this.service.listar();
        HashMap<String, List<Ocurrencia>> resp = new HashMap<>();
        resp.put("ocurrencias", ocurrencias);
        return new ResponseEntity<HashMap<String, List<Ocurrencia>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/ocurrencias/{id}")
    public ResponseEntity<Ocurrencia> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Ocurrencia o = service.obtener(id);

            return new ResponseEntity<Ocurrencia>(o, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ocurrencia>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ocurrencias")
    public ResponseEntity<Ocurrencia> add(@RequestBody Ocurrencia c) throws Exception {
        try {
            Ocurrencia ocurrencia = service.registrar(c);
            return new ResponseEntity<Ocurrencia>(ocurrencia, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ocurrencia>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/ocurrencias/{id}")
    public ResponseEntity<Ocurrencia> update(@RequestBody Ocurrencia o, @PathVariable Long id) throws Exception {
        try {
            service.registrar(o);
            return new ResponseEntity<Ocurrencia>(o, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ocurrencia>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ocurrencias/{id}")
    public ResponseEntity<Ocurrencia> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Ocurrencia>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ocurrencia>(HttpStatus.NOT_FOUND);
        }
    }
}
