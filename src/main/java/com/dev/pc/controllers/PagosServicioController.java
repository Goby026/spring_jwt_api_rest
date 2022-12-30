package com.dev.pc.controllers;

import com.dev.pc.models.PagosServicio;
import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.services.PagosServicioService;
import com.dev.pc.services.PagosServiciosDetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class PagosServicioController {
    
    @Autowired
    private PagosServicioService service;

    @Autowired
    private PagosServiciosDetaService detalleService;

    @GetMapping("/pagos-servicio")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> list() throws Exception {
        List<PagosServicio> pagosservicios = this.service.listar();
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-servicio/cliente/{id}")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> list(@PathVariable(value = "id") Long id ) throws Exception {
        List<PagosServicio> pagosservicios = this.service.listar(id);
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }

//    OBTENER CORRELATIVO
    @GetMapping("/pagos-servicio/count")
    public ResponseEntity<HashMap<String, Integer >> contar() throws Exception {
        int contador = service.contar();
        HashMap<String, Integer> resp = new HashMap<>();
        resp.put("correlativo", contador);
        return new ResponseEntity<HashMap<String, Integer >>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-servicio/caja/{id}")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> listByCaja(@PathVariable(value = "id") Long id ) throws Exception {
        List<PagosServicio> pagosservicios = this.service.listarPorCaja(id);
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/pagos-servicio/{id}")
    public ResponseEntity<PagosServicio> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            PagosServicio c = service.obtener(id);

            return new ResponseEntity<PagosServicio>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServicio>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pagos-servicio")
    public ResponseEntity<PagosServicio> add(@RequestBody PagosServicio pago) throws Exception {
        try {
            pago.setCorrelativo(service.contar() + 1);
            PagosServicio pagosServicio = service.registrar(pago);
            return new ResponseEntity<PagosServicio>(pagosServicio, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServicio>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pagos-servicio/save")
    public ResponseEntity<?> add(@RequestParam("pago") String p,
                                 @RequestParam("detalles") String d) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        PagosServicio pago = mapper.readValue(p, PagosServicio.class);
        PagosServiciosDeta[] detalles = mapper.readValue(d, PagosServiciosDeta[].class);
        List<PagosServiciosDeta> details = Arrays.stream(detalles).toList();

        PagosServicio pagoSaved = service.registrar(pago);

        details.stream().forEach( (det)->{
            det.setPagosServicio(pagoSaved);
        });

        detalleService.registrar(details);
        response.put("pagosservicio", pagoSaved);
        response.put("detalles", details);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/pagos-servicio/{id}")
    public ResponseEntity<PagosServicio> update(@RequestBody PagosServicio c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<PagosServicio>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServicio>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pagos-servicio/{id}")
    public ResponseEntity<PagosServicio> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<PagosServicio>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PagosServicio>(HttpStatus.NOT_FOUND);
        }
    }
}
