package com.dev.pc.controllers;

import com.dev.pc.models.Ingreso;
import com.dev.pc.services.IngresoService;
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
public class IngresoController {
    @Autowired
    private IngresoService service;

    @GetMapping("/ingresos")
    public ResponseEntity<HashMap<String, List<Ingreso>>> list() throws Exception {
        List<Ingreso> ingresos = service.listar();
        HashMap<String, List<Ingreso>> resp = new HashMap<>();
        resp.put("ingresos", ingresos);
        return new ResponseEntity<HashMap<String, List<Ingreso>>>(resp, HttpStatus.OK);
    }

    @PostMapping("/ingresos")
    public ResponseEntity<Ingreso> save( @RequestBody Ingreso i ) throws Exception {
        try {
            Ingreso ingreso = service.registrar(i);
            return new ResponseEntity<Ingreso>(ingreso, HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Ingreso>(HttpStatus.BAD_REQUEST);
        }
    }
}
