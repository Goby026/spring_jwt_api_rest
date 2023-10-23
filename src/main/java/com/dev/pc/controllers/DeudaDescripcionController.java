package com.dev.pc.controllers;

import com.dev.pc.models.DeudaDescripcion;
import com.dev.pc.services.DeudaDescripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class DeudaDescripcionController {

    @Autowired
    private DeudaDescripcionService service;

    @GetMapping("/tipodeudas")
    public ResponseEntity<HashMap<String, List<DeudaDescripcion>>> list() throws Exception {
        List<DeudaDescripcion> tipodeudas = this.service.listar();
        HashMap<String, List<DeudaDescripcion>> resp = new HashMap<>();
        resp.put("tipodeudas", tipodeudas);
        return new ResponseEntity<HashMap<String, List<DeudaDescripcion>>>(resp, HttpStatus.OK);
    }
}
