package com.dev.pc.controllers;

import com.dev.pc.models.Servicio;
import com.dev.pc.services.ServicioService;
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
public class ServicioController {

    @Autowired
    private ServicioService service;

    @GetMapping("/servicios")
    public ResponseEntity<HashMap<String, List<Servicio>>> list() throws Exception {
        List<Servicio> servicios = this.service.listar();
        HashMap<String, List<Servicio>> resp = new HashMap<>();
        resp.put("servicios", servicios);
        return new ResponseEntity<HashMap<String, List<Servicio>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Servicio c = service.obtener(id);

            return new ResponseEntity<Servicio>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicio> add(@RequestBody Servicio c) throws Exception {
        try {
            Servicio servicio = service.registrar(c);
            return new ResponseEntity<Servicio>(servicio, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Servicio>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicio> update(@RequestBody Servicio c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<Servicio>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Servicio> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Servicio>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
        }
    }
}
