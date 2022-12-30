package com.dev.pc.controllers;

import com.dev.pc.models.PagosServicio;
import com.dev.pc.models.Tributo;
import com.dev.pc.services.TributoService;
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
public class TributoController {
    
    @Autowired
    private TributoService service;

    @GetMapping("/tributos")
    public ResponseEntity<HashMap<String, List<Tributo>>> list() throws Exception {
        List<Tributo> tributos = this.service.listar();
        HashMap<String, List<Tributo>> resp = new HashMap<>();
        resp.put("tributos", tributos);
        return new ResponseEntity<HashMap<String, List<Tributo>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tributos/{id}")
    public ResponseEntity<Tributo> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Tributo tributo = service.obtener(id);
            return new ResponseEntity<Tributo>(tributo, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributo>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tributos")
    public ResponseEntity<Tributo> add(@RequestBody Tributo t) throws Exception {
        try {
            t.setCorrelativo(service.contar() + 1);
            Tributo tributo = service.registrar(t);
//            tambien se debe registrar pagoServicio+pagoServicioDetalle
            /*PagosServicio pagosServicio = new PagosServicio();
            pagosServicio.setCosto(null);
            pagosServicio.setCliente(t.getCliente());
            pagosServicio.setMontoapagar(t.getSubtotal());
            pagosServicio.setMontotasas(0.0);
            pagosServicio.setMontodescuento(0.0);
            pagosServicio.setMontopagado(t.getSubtotal());
            pagosServicio.setFecha(t.getCreated_at());
            pagosServicio.setUsuario(t.getUsuario());*/

            return new ResponseEntity<Tributo>(tributo, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributo>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/tributos/{id}")
    public ResponseEntity<Tributo> update(@RequestBody Tributo d, @PathVariable Long id) throws Exception {
        try {
            service.registrar(d);
            return new ResponseEntity<Tributo>(d, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributo>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tributos/{id}")
    public ResponseEntity<Tributo> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Tributo>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributo>(HttpStatus.NOT_FOUND);
        }
    }
}
