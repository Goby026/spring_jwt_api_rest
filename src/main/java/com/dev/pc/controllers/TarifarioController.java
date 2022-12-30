package com.dev.pc.controllers;

import com.dev.pc.models.Tarifario;
import com.dev.pc.services.TarifarioService;
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

}
