package com.dev.pc.controllers;

import com.dev.pc.models.Corte;
import com.dev.pc.models.CorteDetalle;
import com.dev.pc.services.CorteDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class CorteDetalleController {
    private final CorteDetalleService corteDetalleService;

    public CorteDetalleController(CorteDetalleService corteDetalleService) {
        this.corteDetalleService = corteDetalleService;
    }

    @GetMapping("/cortedetalles")
    public ResponseEntity<HashMap<String, List<CorteDetalle>>> list() throws Exception {
        List<CorteDetalle> detalles = this.corteDetalleService.listar();
        HashMap<String, List<CorteDetalle>> resp = new HashMap<>();
        resp.put("detalles", detalles);
        return new ResponseEntity<HashMap<String, List<CorteDetalle>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/cortedetalles/{id}")
    public ResponseEntity<CorteDetalle> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            CorteDetalle corteDetalle = this.corteDetalleService.obtener(id);
            return new ResponseEntity<CorteDetalle>(corteDetalle, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<CorteDetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cortedetalles/bycorte")
    public ResponseEntity<HashMap<String, List<CorteDetalle>>> get(@RequestBody Corte c) throws Exception {
        List<CorteDetalle> detalles = this.corteDetalleService.listar(c);
        HashMap<String, List<CorteDetalle>> resp = new HashMap<>();
        resp.put("detalles", detalles);
        return new ResponseEntity<HashMap<String, List<CorteDetalle>>>(resp, HttpStatus.OK);
    }

    @PostMapping("/cortedetalles")
    public ResponseEntity<CorteDetalle> add(@RequestBody CorteDetalle d) throws Exception {
        try {
            CorteDetalle corteDetalle = this.corteDetalleService.registrar(d);
            return new ResponseEntity<CorteDetalle>(corteDetalle, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<CorteDetalle>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cortedetalles-all")
    public ResponseEntity<List<CorteDetalle>> saveAll(@RequestBody List<CorteDetalle> d) throws Exception {
        try {
            List<CorteDetalle> corteDetalles = this.corteDetalleService.registrarTodos(d);
            return new ResponseEntity<List<CorteDetalle>>(corteDetalles, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<CorteDetalle>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cortedetalles/{id}")
    public ResponseEntity<CorteDetalle> update(@RequestBody CorteDetalle c, @PathVariable Long id) throws Exception {
        try {
            CorteDetalle corteDetalle = this.corteDetalleService.registrar(c);
            return new ResponseEntity<CorteDetalle>(corteDetalle, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<CorteDetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cortedetalles/{id}")
    public ResponseEntity<CorteDetalle> delete(@PathVariable Long id) throws Exception {
        try {
            this.corteDetalleService.eliminar(id);
            return new ResponseEntity<CorteDetalle>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<CorteDetalle>(HttpStatus.NOT_FOUND);
        }
    }
}
