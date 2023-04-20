package com.dev.pc.controllers;

import com.dev.pc.models.Tarifario;
import com.dev.pc.services.TarifarioService;
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
public class TarifarioController {

    private static final Logger logger = LoggerFactory.getLogger(TarifarioController.class);

    @Autowired
    private TarifarioService service;

    @GetMapping("/tarifarios")
    public ResponseEntity<HashMap<String, List<Tarifario>>> list() throws Exception {
        List<Tarifario> tarifas = this.service.listar();
        HashMap<String, List<Tarifario>> resp = new HashMap<>();
        resp.put("tarifas", tarifas);
        return new ResponseEntity<HashMap<String, List<Tarifario>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tarifarios/{id}")
    public ResponseEntity<HashMap<String, List<Tarifario>>> list(@PathVariable(value = "id") Long id) throws Exception {
        List<Tarifario> tarifas = service.listar(id);

        HashMap<String, List<Tarifario>> resp = new HashMap<>();
        resp.put("tarifas", tarifas);
        return new ResponseEntity<HashMap<String, List<Tarifario>>>(resp, HttpStatus.OK);
    }

    @PostMapping("/tarifarios")
    public ResponseEntity<Tarifario> add(@RequestBody Tarifario t) throws Exception {
        try {

            Tarifario tarifario = service.registrar(t);

            return new ResponseEntity<Tarifario>(tarifario, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tarifario>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/tarifarios/{id}")
    public ResponseEntity<Tarifario> update(@RequestBody Tarifario t, @PathVariable Long id) throws Exception {
        try {

            Tarifario tarifa = service.registrar(t);
            return new ResponseEntity<Tarifario>(tarifa, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tarifario>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tarifarios/desactivar")
    public ResponseEntity<Tarifario> desactivar(@RequestBody Tarifario t) throws Exception {
        try {

            if (t.getEsta() == 0){
                t.setEsta(1);
            }else{
                t.setEsta(0);
            }

            Tarifario tarifario = service.registrar(t);

            return new ResponseEntity<Tarifario>(tarifario, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tarifario>(HttpStatus.BAD_REQUEST);
        }
    }

}
