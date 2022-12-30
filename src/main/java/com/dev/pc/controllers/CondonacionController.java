package com.dev.pc.controllers;

import com.dev.pc.models.Condonacion;
import com.dev.pc.services.CondonacionService;
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
public class CondonacionController {
    
    @Autowired
    private CondonacionService service;

    @GetMapping("/condonaciones")
    public ResponseEntity<HashMap<String, List<Condonacion>>> list() throws Exception {
        List<Condonacion> condonaciones = this.service.listar();
        HashMap<String, List<Condonacion>> resp = new HashMap<>();
        resp.put("condonaciones", condonaciones);
        return new ResponseEntity<HashMap<String, List<Condonacion>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/condonaciones/{id}")
    public ResponseEntity<Condonacion> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Condonacion condonacion = service.obtener(id);
            return new ResponseEntity<Condonacion>(condonacion, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Condonacion>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/condonaciones")
    public ResponseEntity<Condonacion> add(@RequestBody Condonacion d) throws Exception {
        try {
            Condonacion condonacion = service.registrar(d);
            return new ResponseEntity<Condonacion>(condonacion, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Condonacion>(HttpStatus.BAD_REQUEST);
        }
    }

//    REGISTRAR VARIAS CONDONACIONES
    @PostMapping("/condonaciones/service")
    public ResponseEntity<HashMap<String, List<Condonacion>>> add(@RequestBody List<Condonacion> p) throws Exception {
        try {
            List<Condonacion> condonaciones = service.registrar(p);
            HashMap<String, List<Condonacion>> resp = new HashMap<>();
            resp.put("condonaciones", condonaciones);
            return new ResponseEntity<HashMap<String, List<Condonacion>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<Condonacion>>>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/condonaciones/{id}")
    public ResponseEntity<Condonacion> update(@RequestBody Condonacion c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<Condonacion>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Condonacion>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/condonaciones/{id}")
    public ResponseEntity<Condonacion> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Condonacion>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Condonacion>(HttpStatus.NOT_FOUND);
        }
    }
}
