package com.dev.pc.controllers;

import com.dev.pc.models.Cliente;
import com.dev.pc.models.Costo;
import com.dev.pc.services.CostoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CostoController {

    private static final Logger logger = LoggerFactory.getLogger(CostoController.class);

    @Autowired
    private CostoService service;

    //listar todos los costos presenta problemas porla inconsistencia de la BBDD
    @GetMapping("/costos")
    public ResponseEntity<HashMap<String, List<Costo>>> list() throws Exception {
        List<Costo> costos = this.service.listar();
        HashMap<String, List<Costo>> resp = new HashMap<>();
        resp.put("costos", costos);
        return new ResponseEntity<HashMap<String, List<Costo>>>(resp, HttpStatus.OK);
    }

    //buscar costos por id de cliente
    @GetMapping("/costos/buscar/{id}")
        public ResponseEntity<HashMap<String, List<Costo>>> listar(@PathVariable(value = "id") Long id) throws Exception {
        List<Costo> costos = this.service.listar(id);
        HashMap<String, List<Costo>> resp = new HashMap<>();
        resp.put("costos", costos);
        return new ResponseEntity<HashMap<String, List<Costo>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/costos/{id}")
    public ResponseEntity<Costo> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Costo costo = service.obtener(id);
            return new ResponseEntity<Costo>(costo, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costo>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/costos")
    public ResponseEntity<Costo> add(@RequestBody Costo obj) throws Exception {
        try {
            logger.info("OBJETO COSTO---------------------------->" + obj);
            Costo costo = service.registrar(obj);
            return new ResponseEntity<Costo>(costo, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costo>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/costos/{id}")
    public ResponseEntity<Costo> update(@RequestBody Costo d, @PathVariable Long id) throws Exception {
        try {
            Costo costo = service.registrar(d);
            return new ResponseEntity<Costo>(costo, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costo>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/costos/{id}")
    public ResponseEntity<Costo> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Costo>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costo>(HttpStatus.NOT_FOUND);
        }
    }
}
