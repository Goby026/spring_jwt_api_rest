package com.dev.pc.controllers;

import com.dev.pc.models.PagosServicio;
import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.models.Tributo;
import com.dev.pc.services.PagosServicioService;
import com.dev.pc.services.PagosServiciosDetaService;
import com.dev.pc.services.TributoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class TributoController {
    
    @Autowired
    private TributoService service;

    @Autowired
    private PagosServicioService pagoService;

    @Autowired
    private PagosServiciosDetaService pagoDetalleService;

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
            pagosServicio.setUsuario(t.getUser());*/

            return new ResponseEntity<Tributo>(tributo, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Tributo>(HttpStatus.BAD_REQUEST);
        }
    }

//    método para registrar pago y detalle de pago de un tributo
/*    @PostMapping("/tributos/save")
    public ResponseEntity<?> add(@RequestParam("pagoservicio") String p,
                                 @RequestParam("detalles") String d) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        PagosServicio pago = mapper.readValue(p, PagosServicio.class);

        PagosServiciosDeta[] detalles = mapper.readValue(d, PagosServiciosDeta[].class);
        List<PagosServiciosDeta> details = Arrays.stream(detalles).toList();

        PagosServicio pagoSaved = pagoService.registrar(pago);

        details.stream().forEach( (det)->{
            det.setPagosServicio(pagoSaved);
        });

        pagoDetalleService.registrar(details);
        response.put("pago", pagoSaved);
        response.put("detalles", details);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }*/

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
