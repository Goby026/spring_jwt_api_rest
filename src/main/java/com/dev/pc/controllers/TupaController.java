package com.dev.pc.controllers;

import com.dev.pc.models.Tupa;
import com.dev.pc.services.TupaService;
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
public class TupaController {

    @Autowired
    private TupaService service;

    @GetMapping("/tupas")
    public ResponseEntity<HashMap<String, List<Tupa>>> list() throws Exception {
        List<Tupa> tupas = this.service.listar();
        HashMap<String, List<Tupa>> resp = new HashMap<>();
        resp.put("tupas", tupas);
        return new ResponseEntity<HashMap<String, List<Tupa>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tupas/{id}")
    public ResponseEntity<Tupa> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Tupa tupa = service.obtener(id);
            return new ResponseEntity<Tupa>(tupa, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tupa>(HttpStatus.NOT_FOUND);
        }
    }

}
