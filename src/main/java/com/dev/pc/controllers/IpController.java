package com.dev.pc.controllers;

import com.dev.pc.models.Ip;
import com.dev.pc.services.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class IpController {

    @Autowired
    private IpService service;

    @GetMapping("/ips")
    public List<Ip> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/ips/{id}")
    public ResponseEntity<Ip> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Ip ip = service.obtener(id);

            return new ResponseEntity<Ip>(ip, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ip>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ips")
    public ResponseEntity<Ip> add( @RequestBody Ip req_ip) throws Exception {

        try {

            Ip ip = service.registrar(req_ip);

            return new ResponseEntity<Ip>(ip, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Ip>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/ips/{id}")
    public ResponseEntity<Ip> update( @RequestBody Ip req_ip ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(req_ip);

            return new ResponseEntity<Ip>(req_ip,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Ip>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/ips/{id}")
    public ResponseEntity<Ip> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Ip>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Ip>(HttpStatus.NOT_FOUND);
        }
    }

}
