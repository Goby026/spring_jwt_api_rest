package com.dev.pc.controllers;

import com.dev.pc.models.Costo;
import com.dev.pc.models.Costootroservicio;
import com.dev.pc.models.Tarifario;
import com.dev.pc.services.CostoService;
import com.dev.pc.services.CostootroservicioService;
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
public class CostootroservicioController {

    public static final Logger logger = LoggerFactory.getLogger(CostootroservicioController.class);
    
    @Autowired
    private CostootroservicioService service;

    @Autowired
    private CostoService costoService;

    @GetMapping("/costo-otros")
    public ResponseEntity<HashMap<String, List<Object>>> list() throws Exception {
//        List<Costootroservicio> costootros = this.service.listar();
        List<Object> costootros = List.of(this.service.listar().stream().filter(s -> (s.getCosto().getCliente() != null)).toArray());
        HashMap<String, List<Object>> resp = new HashMap<>();
        resp.put("costootros", costootros);
        return new ResponseEntity<HashMap<String, List<Object>>>(resp, HttpStatus.OK);
    }

    //    buscar por id de costo
    @GetMapping("/costo-otros/buscar/{id}")
    public ResponseEntity<HashMap<String, List<Costootroservicio>>> list(@PathVariable(value = "id") Long id) throws Exception {
        List<Costootroservicio> costootros = this.service.listar(id);
        HashMap<String, List<Costootroservicio>> resp = new HashMap<>();
        resp.put("costootros", costootros);
        return new ResponseEntity<HashMap<String, List<Costootroservicio>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Costootroservicio c = service.obtener(id);

            return new ResponseEntity<Costootroservicio>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/costo-otros")
    public ResponseEntity<Costootroservicio> add(@RequestBody Costootroservicio c) throws Exception {

        /*Costo costo = costoService.obtener(c.getCosto().getCodcosto());
        Tarifario tarifario = */


        try {
            Costootroservicio costootro = service.registrar(c);
            return new ResponseEntity<Costootroservicio>(costootro, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> update(@RequestBody Costootroservicio c, @PathVariable Long id) throws Exception {
        try {

            Costootroservicio co = service.obtener(id);

            service.registrar(c);
            return new ResponseEntity<Costootroservicio>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/costo-otros/{id}")
    public ResponseEntity<Costootroservicio> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Costootroservicio>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Costootroservicio>(HttpStatus.NOT_FOUND);
        }
    }
    
}
