package com.dev.pc.controllers;

import com.dev.pc.models.Corte;
import com.dev.pc.models.PagosServicio;
import com.dev.pc.services.CorteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class CorteController {

    CorteService corteService;

    public CorteController(CorteService corteService) {
        this.corteService = corteService;
    }

    @GetMapping("/cortes")
    public ResponseEntity<HashMap<String, List<Corte>>> list() throws Exception {
        List<Corte> cortes = this.corteService.listar();
        HashMap<String, List<Corte>> resp = new HashMap<>();
        resp.put("cortes", cortes);
        return new ResponseEntity<HashMap<String, List<Corte>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/cortes/{id}")
    public ResponseEntity<Corte> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Corte corte = corteService.obtener(id);
            return new ResponseEntity<Corte>(corte, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Corte>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cortes")
    public ResponseEntity<Corte> add(@RequestBody Corte d) throws Exception {
        try {
            Corte corte = corteService.registrar(d);
            return new ResponseEntity<Corte>(corte, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Corte>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/cortes/{id}")
    public ResponseEntity<Corte> update(@RequestBody Corte c, @PathVariable Long id) throws Exception {
        try {
            Corte corte = this.corteService.registrar(c);
            return new ResponseEntity<Corte>(corte, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Corte>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cortes/{id}")
    public ResponseEntity<Corte> delete(@PathVariable Long id) throws Exception {
        try {
            corteService.eliminar(id);
            return new ResponseEntity<Corte>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Corte>(HttpStatus.NOT_FOUND);
        }
    }
}
