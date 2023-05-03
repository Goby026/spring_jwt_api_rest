package com.dev.pc.controllers;

import com.dev.pc.models.Egreso;
import com.dev.pc.services.EgresoService;
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
public class EgresoController {

    @Autowired
    private EgresoService service;

    @GetMapping("/egresos")
    public ResponseEntity<HashMap<String, List<Egreso>>> list() throws Exception {
        List<Egreso> egresos = this.service.listar();
        HashMap<String, List<Egreso>> resp = new HashMap<>();
        resp.put("egresos", egresos);
        return new ResponseEntity<HashMap<String, List<Egreso>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/egresos/{id}")
    public ResponseEntity<Egreso> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Egreso egreso = service.obtener(id);

            return new ResponseEntity<Egreso>(egreso, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Egreso>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/egresos")
    public ResponseEntity<Egreso> add(@RequestBody Egreso eg) throws Exception {
        try {
            Egreso egreso = service.registrar(eg);
            return new ResponseEntity<Egreso>(egreso, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Egreso>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/egresos/{id}")
    public ResponseEntity<Egreso> update(@RequestBody Egreso e, @PathVariable Long id) throws Exception {
        try {
            Egreso egreso = service.registrar(e);
            return new ResponseEntity<Egreso>(egreso, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Egreso>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/egresos/{id}")
    public ResponseEntity<Egreso> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Egreso>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Egreso>(HttpStatus.NOT_FOUND);
        }
    }

//    OBTENER EGRESOS POR ID DE CAJA
    @GetMapping("/egresos/caja/{idcaja}")
    public ResponseEntity<HashMap<String, List<Egreso>>> listByCaja(@PathVariable Long idcaja) throws Exception {
        List<Egreso> egresos = this.service.listarPorCaja(idcaja);
        HashMap<String, List<Egreso>> resp = new HashMap<>();
        resp.put("egresos", egresos);
        return new ResponseEntity<HashMap<String, List<Egreso>>>(resp, HttpStatus.OK);
    }

}
