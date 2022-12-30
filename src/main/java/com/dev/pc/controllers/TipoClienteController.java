package com.dev.pc.controllers;

import com.dev.pc.models.TipoCliente;
import com.dev.pc.services.TipoClienteService;
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
public class TipoClienteController {
    
    @Autowired
    private TipoClienteService service;

    @GetMapping("/tipoClientes")
    public ResponseEntity<HashMap<String, List<TipoCliente>>> list() throws Exception {
        List<TipoCliente> tipoClientes = this.service.listar();
        HashMap<String, List<TipoCliente>> resp = new HashMap<>();
        resp.put("tipoClientes", tipoClientes);
        return new ResponseEntity<HashMap<String, List<TipoCliente>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/tipoClientes/{id}")
    public ResponseEntity<TipoCliente> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            TipoCliente tipoCliente = service.obtener(id);
            return new ResponseEntity<TipoCliente>(tipoCliente, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoCliente>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tipoClientes")
    public ResponseEntity<TipoCliente> add(@RequestBody TipoCliente d) throws Exception {
        try {
            TipoCliente tipoCliente = service.registrar(d);
            return new ResponseEntity<TipoCliente>(tipoCliente, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoCliente>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/tipoClientes/{id}")
    public ResponseEntity<TipoCliente> update(@RequestBody TipoCliente d, @PathVariable Long id) throws Exception {
        try {
            service.registrar(d);
            return new ResponseEntity<TipoCliente>(d, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoCliente>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tipoClientes/{id}")
    public ResponseEntity<TipoCliente> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<TipoCliente>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TipoCliente>(HttpStatus.NOT_FOUND);
        }
    }
}
