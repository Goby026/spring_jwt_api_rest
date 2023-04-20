package com.dev.pc.controllers;

import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.services.PagosServiciosDetaService;
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
public class PagosServicioDetaController {

    @Autowired
    private PagosServiciosDetaService service;

    @GetMapping("/pagos-deta")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> list() throws Exception {
        List<PagosServiciosDeta> pagos = this.service.listar();
        HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
        resp.put("pagosdeta", pagos);
        return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-deta/cliente/{id}")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> list(@PathVariable(value = "id") Long id) throws Exception {
        List<PagosServiciosDeta> pagos = this.service.listar(id);
        HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
        resp.put("pagosdeta", pagos);
        return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-deta/anio/{idcliente}/{anio}")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> list(@PathVariable(value = "idcliente") Long idcliente,
                                                                          @PathVariable(value = "anio") int anio) throws Exception {
        List<PagosServiciosDeta> pagos = this.service.listarPorClienteAnio(idcliente, anio);
        HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
        resp.put("pagosdeta", pagos);
        return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
    }

//    controlador para obtener detalles de pago segun rango de fechas
    @GetMapping("/pagos-deta/fechas/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> listarFechas(@PathVariable(value = "desde") String desde,
                                                                          @PathVariable(value = "hasta") String hasta) throws Exception {

        Date inicio =  new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<PagosServiciosDeta> pagos = this.service.listarPorFechas(inicio, fin);
        HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
        resp.put("pagosdeta", pagos);
        return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-deta/pago/{idpago}")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> listByPago(@PathVariable(value = "idpago") Long idpago) throws Exception {
        List<PagosServiciosDeta> pagos = this.service.listarPorPago(idpago);
        HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
        resp.put("pagosdeta", pagos);
        return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
    }


    @GetMapping("/pagos-deta/{id}")
    public ResponseEntity<PagosServiciosDeta> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            PagosServiciosDeta pago = service.obtener(id);
            return new ResponseEntity<PagosServiciosDeta>(pago, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServiciosDeta>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pagos-deta")
    public ResponseEntity<PagosServiciosDeta> add(@RequestBody PagosServiciosDeta d) throws Exception {
        try {
            PagosServiciosDeta pago = service.registrar(d);
            return new ResponseEntity<PagosServiciosDeta>(pago, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServiciosDeta>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/pagos-deta/service")
    public ResponseEntity<HashMap<String, List<PagosServiciosDeta>>> add(@RequestBody List<PagosServiciosDeta> pagos) throws Exception {
        try {
            List<PagosServiciosDeta> detalles = service.registrar(pagos);
            HashMap<String, List<PagosServiciosDeta>> resp = new HashMap<>();
            resp.put("pagosdeta", detalles);
            return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<PagosServiciosDeta>>>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/pagos-deta/{id}")
    public ResponseEntity<PagosServiciosDeta> update(@RequestBody PagosServiciosDeta d, @PathVariable Long id) throws Exception {
        try {
            service.registrar(d);
            return new ResponseEntity<PagosServiciosDeta>(d, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServiciosDeta>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pagos-deta/{id}")
    public ResponseEntity<PagosServiciosDeta> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<PagosServiciosDeta>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServiciosDeta>(HttpStatus.NOT_FOUND);
        }
    }

}
