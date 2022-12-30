package com.dev.pc.controllers;

import com.dev.pc.models.Zona;
import com.dev.pc.services.ZonaService;
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
public class ZonaController {
    
    @Autowired
    private ZonaService service;

    @GetMapping("/zonas")
    public ResponseEntity<HashMap<String, List<Zona>>> list() throws Exception {
        List<Zona> zonas = this.service.listar();
        HashMap<String, List<Zona>> resp = new HashMap<>();
        resp.put("zonas", zonas);
        return new ResponseEntity<HashMap<String, List<Zona>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/zonas/{id}")
    public ResponseEntity<Zona> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Zona zona = service.obtener(id);
            return new ResponseEntity<Zona>(zona, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Zona>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/zonas")
    public ResponseEntity<Zona> add(@RequestBody Zona d) throws Exception {
        try {
            Zona zona = service.registrar(d);
            return new ResponseEntity<Zona>(zona, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Zona>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/zonas/{id}")
    public ResponseEntity<Zona> update(@RequestBody Zona d, @PathVariable Long id) throws Exception {
        try {
            service.registrar(d);
            return new ResponseEntity<Zona>(d, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Zona>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/zonas/{id}")
    public ResponseEntity<Zona> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Zona>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Zona>(HttpStatus.NOT_FOUND);
        }
    }
    
}
