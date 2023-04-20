package com.dev.pc.controllers;

import com.dev.pc.models.Deuda;
import com.dev.pc.models.DeudaEstado;
import com.dev.pc.services.DeudaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class DeudaController {

    private static final Logger logger = LoggerFactory.getLogger(DeudaController.class);

    @Autowired
    private DeudaService service;

    @GetMapping("/deudas")
    public ResponseEntity<HashMap<String, List<Deuda>>> list() throws Exception {
        List<Deuda> deudas = this.service.listar();
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

//    buscar deudas por id de cliente
    @GetMapping("/deudas/buscar/{id}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listar(@PathVariable(value = "id") Long id) throws Exception {
        List<Deuda> deudas = this.service.listar(id);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* BUSCAR DEUDAS DE ZONA POR RANGO DE FECHAS */
    @GetMapping("/deudas/buscar-zona/{idzona}/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorZonaAnnio(@PathVariable(value = "idzona") Long idzona,
                                                                           @PathVariable(value = "desde") String desde,
                                                                           @PathVariable(value = "hasta") String hasta) throws Exception {

        Date dateInicio =  new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Deuda> deudas = this.service.listarPorZonaAnnio(idzona,dateInicio, dateFin);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* BUSCAR DEUDAS DE CLIENTE POR RANGO DE FECHAS */
    @GetMapping("/deudas/buscar-cliente/{idcliente}/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorClienteAnnio(@PathVariable(value = "idcliente") Long idcliente,
                                                                           @PathVariable(value = "desde") String desde,
                                                                           @PathVariable(value = "hasta") String hasta) throws Exception {

        Date dateInicio =  new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Deuda> deudas = this.service.listarPorClienteAnnio(idcliente,dateInicio, dateFin);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /*    DEUDAS POR ZONA */
    @GetMapping("/deudas/buscar-zona/{idzona}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorZona(@PathVariable(value = "idzona") Long idzona) throws Exception {

        List<Deuda> deudas = this.service.listarPorZona(idzona);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/deudas/{id}")
    public ResponseEntity<Deuda> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Deuda deuda = service.obtener(id);
            return new ResponseEntity<Deuda>(deuda, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deudas")
    public ResponseEntity<Deuda> add(@RequestBody Deuda d) throws Exception {
        try {
            Deuda deuda = service.registrar(d);
            return new ResponseEntity<Deuda>(deuda, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/deudas/{id}")
    public ResponseEntity<Deuda> update(@RequestBody DeudaEstado d, @PathVariable Long id) throws Exception {
        try {
            Deuda deuda = service.obtener(id);
            deuda.setDeudaEstado(d);
            service.registrar(deuda);
            return new ResponseEntity<Deuda>(deuda, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.NOT_FOUND);
        }
    }

    //    api rest para actualizar estado de las deudas
    @PreAuthorize("permitAll()")
    @PutMapping("/deudas/service")
    public ResponseEntity<HashMap<String, List<Deuda>>> update(@RequestBody List<Deuda> deudas) throws Exception {
        try {
            service.registrar(deudas);
            HashMap<String, List<Deuda>> resp = new HashMap<>();
            resp.put("deudas", deudas);
            return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<Deuda>>>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/deudas/{id}")
    public ResponseEntity<Deuda> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Deuda>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/generar-deudas")
    public ResponseEntity<?> addDeudas() throws Exception {
        try {

            service.generarDeudaAnnio();

            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }

}
