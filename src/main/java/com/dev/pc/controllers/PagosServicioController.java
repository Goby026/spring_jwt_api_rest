package com.dev.pc.controllers;

import com.dev.pc.models.Caja;
import com.dev.pc.models.PagosServicio;
import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.services.CajaService;
import com.dev.pc.services.PagosServicioService;
import com.dev.pc.services.PagosServiciosDetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class PagosServicioController {

    private static final Logger logger = LoggerFactory.getLogger(PagosServicioController.class);
    
    @Autowired
    private PagosServicioService service;

    @Autowired
    private CajaService cajaService;

    @Autowired
    private PagosServiciosDetaService detalleService;

    @GetMapping("/pagos-servicio")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> list() throws Exception {
        List<PagosServicio> pagosservicios = this.service.listar();
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }

//    listar pagos por id de cliente
    @GetMapping("/pagos-servicio/cliente/{id}")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> list(@PathVariable(value = "id") Long id ) throws Exception {
        List<PagosServicio> pagosservicios = this.service.listar(id);
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }

//  LISTAR POR TRIBUTO
@GetMapping("/pagos-servicio/tributo/{idtributo}/{desde}/{hasta}")
public ResponseEntity<HashMap<String, List<PagosServicio>>> listByReqAndDates(@PathVariable(value = "idtributo") Long idtributo, @PathVariable(value = "desde") String desde,
                                                                               @PathVariable(value = "hasta") String hasta) throws Exception {
    Date inicio =  new SimpleDateFormat("yyyy-MM-dd").parse(desde);
    Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

    List<PagosServicio> pagos = this.service.listarPorTributo(idtributo, inicio, fin);
    HashMap<String, List<PagosServicio>> resp = new HashMap<>();
    resp.put("pagos", pagos);
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

    @PreAuthorize("permitAll()")
    @PostMapping("/pagos-servicio/save")
    public ResponseEntity<?> regAll(@RequestParam("pagoservicio") String p,
                                 @RequestParam("detalles") String d) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        PagosServicio pago = mapper.readValue(p, PagosServicio.class);
        PagosServiciosDeta[] detalles = mapper.readValue(d, PagosServiciosDeta[].class);
        List<PagosServiciosDeta> details = new ArrayList<>();
        for (PagosServiciosDeta detalle : detalles) {
            details.add(detalle);
        }

        pago.setCorrelativo(service.contar());
        PagosServicio pagoSaved = service.registrar(pago);

        details.stream().forEach( (det)->{
            det.setPagosServicio(pagoSaved);
        });

        detalleService.registrar(details);

        //acumular montos pagados para actualizar caja
        Double  monto = 0.00;
        List<PagosServicio> pagos = service.listarPorCaja(pago.getCaja().getIdcaja());
        if (pagos.size() <= 0){
            monto = pago.getMontopagado();
        }else{
            for (PagosServicio pg : pagos) {
                monto += pg.getMontopagado();
            }
        }
        //actualizar caja en cada transacción
        Caja caja = cajaService.obtener(pago.getCaja().getIdcaja());
        caja.setTotal(monto);
        cajaService.registrar(caja);

        response.put("pagosservicio", pagoSaved);
        response.put("detalles", details);
        response.put("caja", caja);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/pagos-servicio/{id}")
    public ResponseEntity<?> update(@RequestBody PagosServicio p, @PathVariable Long id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
//            PagosServicio pago = service.registrar(p);
            PagosServicio pago = service.obtener(id);

            service.registrar(p);

            //acumular montos pagados para actualizar caja
            Double  monto = 0.00;
            List<PagosServicio> pagos = service.listarPorCaja(pago.getCaja().getIdcaja());
            if (pagos.size() <= 0){
                monto = pago.getMontopagado();
            }else{
                for (PagosServicio pg : pagos) {
                    monto += pg.getMontopagado();
                }
            }

            //actualizar caja en cada transacción
            Caja caja = cajaService.obtener(pago.getCaja().getIdcaja());
            caja.setTotal(monto);
            cajaService.registrar(caja);

            response.put("caja", caja);
            response.put("pagosservicio", pago);


//            return new ResponseEntity<PagosServicio>(pago, HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
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

    @GetMapping("/pagos-servicio/year-month/{param}")
    public ResponseEntity<HashMap<String, List<PagosServicio>>> listByYearMonth(@PathVariable(value = "param") String param) throws Exception {
        List<PagosServicio> pagosservicios = this.service.listarPorYearMonth(param);
        HashMap<String, List<PagosServicio>> resp = new HashMap<>();
        resp.put("pagosservicios", pagosservicios);
        return new ResponseEntity<HashMap<String, List<PagosServicio>>>(resp, HttpStatus.OK);
    }
}
