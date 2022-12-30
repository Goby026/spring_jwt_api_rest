package com.dev.pc.controllers;

import com.dev.pc.models.Costootroservicio;
import com.dev.pc.services.CostootroservicioService;
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
public class CostootroservicioController {
    
    @Autowired
    private CostootroservicioService service;

    @GetMapping("/costo-otros")
    public ResponseEntity<HashMap<String, List<Costootroservicio>>> list() throws Exception {
        List<Costootroservicio> costootros = this.service.listar();
        HashMap<String, List<Costootroservicio>> resp = new HashMap<>();
        resp.put("costootros", costootros);
        return new ResponseEntity<HashMap<String, List<Costootroservicio>>>(resp, HttpStatus.OK);
    }

    //    buscar por id de costo
    @GetMapping("/costo-otros/buscar/{id}")
    public ResponseEntity<HashMap<String, List<Costootroservicio>>> list(@PathVariable(value = "id") Long id) throws Exception {
        List<Costootroservicio> costootros = this.service.listar(id);
        HashMap<String, List<Costootroservicio>> resp = new HashMap<>();
        resp.put("costootros", costootros);
        return new ResponseEntity<HashMap<String, List<Costootroservicio>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Costootroservicio c = service.obtener(id);

            return new ResponseEntity<Costootroservicio>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/costo-otros")
    public ResponseEntity<Costootroservicio> add(@RequestBody Costootroservicio c) throws Exception {
        try {
            Costootroservicio costootro = service.registrar(c);
            return new ResponseEntity<Costootroservicio>(costootro, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> update(@RequestBody Costootroservicio c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<Costootroservicio>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Costootroservicio>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }
    
}
