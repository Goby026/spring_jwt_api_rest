package com.dev.pc.controllers;

import com.dev.pc.models.Tributodetalle;
import com.dev.pc.services.TributoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class TributoDetalleController {
    
    @Autowired
    private TributoDetalleService service;

    @GetMapping("/tributo-detalles")
    public ResponseEntity<HashMap<String, List<Tributodetalle>>> list() throws Exception {
        List<Tributodetalle> detalles = this.service.listar();
        HashMap<String, List<Tributodetalle>> resp = new HashMap<>();
        resp.put("detalles", detalles);
        return new ResponseEntity<HashMap<String, List<Tributodetalle>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tributo-detalles/req/{codrequi}")
    public ResponseEntity<HashMap<String, List<Tributodetalle>>> listByReq(@PathVariable(value = "codrequi") Long codrequi) throws Exception {
        List<Tributodetalle> detalles = this.service.listarPorRequisito(codrequi);
        HashMap<String, List<Tributodetalle>> resp = new HashMap<>();
        resp.put("detalles", detalles);
        return new ResponseEntity<HashMap<String, List<Tributodetalle>>>(resp, HttpStatus.OK);
    }

//    API PARA LISTAR PAGOS POR REQUISITO Y RANGO DE FECHA
    @GetMapping("/tributo-detalles/reporte/{codrequi}/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Tributodetalle>>> listByReqAndDates(@PathVariable(value = "codrequi") Long codrequi, @PathVariable(value = "desde") String desde,
                                                                                   @PathVariable(value = "hasta") String hasta) throws Exception {
        Date inicio =  new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Tributodetalle> detalles = this.service.listarPorRequisitoDates(codrequi, inicio, fin);
        HashMap<String, List<Tributodetalle>> resp = new HashMap<>();
        resp.put("detalles", detalles);
        return new ResponseEntity<HashMap<String, List<Tributodetalle>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tributo-detalles/{id}")
    public ResponseEntity<Tributodetalle> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Tributodetalle c = service.obtener(id);
            return new ResponseEntity<Tributodetalle>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributodetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tributo-detalles")
    public ResponseEntity<Tributodetalle> add(@RequestBody Tributodetalle c) throws Exception {
        try {
            Tributodetalle detalles = service.registrar(c);
            return new ResponseEntity<Tributodetalle>(detalles, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributodetalle>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/tributo-detalles/service")
    public ResponseEntity<HashMap<String, List<Tributodetalle>>> add(@RequestBody List<Tributodetalle> d) throws Exception {
        try {
            List<Tributodetalle> detalles = service.registrar(d);
            HashMap<String, List<Tributodetalle>> resp = new HashMap<>();
            resp.put("detalles", detalles);
            return new ResponseEntity<HashMap<String, List<Tributodetalle>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<Tributodetalle>>>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/tributo-detalles/{id}")
    public ResponseEntity<Tributodetalle> update(@RequestBody Tributodetalle c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<Tributodetalle>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributodetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tributo-detalles/{id}")
    public ResponseEntity<Tributodetalle> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Tributodetalle>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributodetalle>(HttpStatus.NOT_FOUND);
        }
    }
}
