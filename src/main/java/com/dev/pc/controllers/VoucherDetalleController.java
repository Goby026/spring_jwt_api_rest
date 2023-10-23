package com.dev.pc.controllers;

import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.models.VoucherDetalle;
import com.dev.pc.services.VoucherDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class VoucherDetalleController {
    
    @Autowired
    private VoucherDetalleService service;

    @GetMapping("/voucher-detalles")
    public ResponseEntity<HashMap<String, List<VoucherDetalle>>> list() throws Exception {
        List<VoucherDetalle> voucherdetalles = this.service.listar();
        HashMap<String, List<VoucherDetalle>> resp = new HashMap<>();
        resp.put("voucherdetalles", voucherdetalles);
        return new ResponseEntity<HashMap<String, List<VoucherDetalle>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/voucher-detalles/voucher/{idvoucher}")
    public ResponseEntity<HashMap<String, List<VoucherDetalle>>> listByVoucher(@PathVariable(value = "idvoucher")Long idvoucher) throws Exception {
        List<VoucherDetalle> voucherdetalles = this.service.listar(idvoucher);
        HashMap<String, List<VoucherDetalle>> resp = new HashMap<>();
        resp.put("voucherdetalles", voucherdetalles);
        return new ResponseEntity<HashMap<String, List<VoucherDetalle>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/voucher-detalles/{id}")
    public ResponseEntity<VoucherDetalle> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            VoucherDetalle c = service.obtener(id);

            return new ResponseEntity<VoucherDetalle>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<VoucherDetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/voucher-detalles")
    public ResponseEntity<VoucherDetalle> add(@RequestBody VoucherDetalle c) throws Exception {
        try {
            VoucherDetalle voucherdetalle = service.registrar(c);
            return new ResponseEntity<VoucherDetalle>(voucherdetalle, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<VoucherDetalle>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/voucher-detalles/service")
    public ResponseEntity<HashMap<String, List<VoucherDetalle>>> add(@RequestBody List<VoucherDetalle> d) throws Exception {
        try {
            List<VoucherDetalle> detalles = service.registrarTodos(d);
            HashMap<String, List<VoucherDetalle>> resp = new HashMap<>();
            resp.put("detalles", detalles);
            return new ResponseEntity<HashMap<String, List<VoucherDetalle>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<VoucherDetalle>>>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/voucher-detalles/{id}")
    public ResponseEntity<VoucherDetalle> update(@RequestBody VoucherDetalle c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<VoucherDetalle>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<VoucherDetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/voucher-detalles/{id}")
    public ResponseEntity<VoucherDetalle> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<VoucherDetalle>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<VoucherDetalle>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/voucher-detalles/cliente/{idcliente}")
    public ResponseEntity<HashMap<String, List<VoucherDetalle>>> listByClientId( @PathVariable(value = "idcliente") Long idcliente ) throws Exception {
        List<VoucherDetalle> voucherdetalles = this.service.listarPorCliente(idcliente);
        HashMap<String, List<VoucherDetalle>> resp = new HashMap<>();
        resp.put("voucherdetalles", voucherdetalles);
        return new ResponseEntity<HashMap<String, List<VoucherDetalle>>>(resp, HttpStatus.OK);
    }
}
