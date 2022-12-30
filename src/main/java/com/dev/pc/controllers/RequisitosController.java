package com.dev.pc.controllers;

import com.dev.pc.models.Requisitos;
import com.dev.pc.models.Tupa;
import com.dev.pc.services.RequisitosService;
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
public class RequisitosController {

    @Autowired
    private RequisitosService service;

    @GetMapping("/requisitos")
    public ResponseEntity<HashMap<String, List<Requisitos>>> list() throws Exception {
        List<Requisitos> requisitos = this.service.listar();
        HashMap<String, List<Requisitos>> resp = new HashMap<>();
        resp.put("requisitos", requisitos);
        return new ResponseEntity<HashMap<String, List<Requisitos>>>(resp, HttpStatus.OK);
    }

    @PostMapping("/requisitos/tupa")
    public ResponseEntity<HashMap<String, List<Requisitos>>> listar(@RequestBody Tupa tupa) throws Exception {
        List<Requisitos> requisitos = this.service.listarPorTupa(tupa);
        HashMap<String, List<Requisitos>> resp = new HashMap<>();
        resp.put("requisitos", requisitos);
        return new ResponseEntity<HashMap<String, List<Requisitos>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/requisitos/{id}")
    public ResponseEntity<Requisitos> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Requisitos requisito = service.obtener(id);
            return new ResponseEntity<Requisitos>(requisito, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Requisitos>(HttpStatus.NOT_FOUND);
        }
    }

}
