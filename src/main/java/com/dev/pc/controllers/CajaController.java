package com.dev.pc.controllers;

import com.dev.pc.models.Caja;
import com.dev.pc.models.PagosServicio;
import com.dev.pc.services.CajaService;
import com.dev.pc.services.PagosServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CajaController {

    private static final Logger logger = LoggerFactory.getLogger(CajaController.class);
    
    @Autowired
    private CajaService service;

    @Autowired
    private PagosServicioService pagosService;

    @GetMapping("/cajas")
    public ResponseEntity<HashMap<String, List<Caja>>> list() throws Exception {
        List<Caja> cajas = this.service.listarAsc();
        HashMap<String, List<Caja>> resp = new HashMap<>();
        resp.put("cajas", cajas);
        return new ResponseEntity<HashMap<String, List<Caja>>>(resp, HttpStatus.OK);
    }

//    BUSQUEDA POR FECHA DE APERTURA
    @GetMapping("/cajas/buscar/{fecha}")
    public ResponseEntity<HashMap<String, List<Caja>>> list(@PathVariable(value = "fecha") String fecha) throws Exception {
        Date fec = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        List<Caja> cajas = this.service.listar(fec);
        HashMap<String, List<Caja>> resp = new HashMap<>();
        resp.put("cajas", cajas);
        return new ResponseEntity<HashMap<String, List<Caja>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/cajas/{id}")
    public ResponseEntity<Caja> get(@PathVariable(value = "id") Long id) throws Exception {
        try {
            Caja caja = service.obtener(id);
            return new ResponseEntity<Caja>(caja, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Caja>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cajas")
    public ResponseEntity<Caja> add(@RequestBody Caja d) throws Exception {
        try {
            Caja caja = service.registrar(d);
            return new ResponseEntity<Caja>(caja, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Caja>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/cajas/{id}")
    public ResponseEntity<Caja> update(@RequestBody Caja caja, @PathVariable Long id) throws Exception {
        try {
//            obtener pagos por caja
            List<PagosServicio> pagos = pagosService.listarPorCaja(id);
            service.persistirMonto(pagos, caja);
//            service.registrar(caja);
            return new ResponseEntity<Caja>(caja, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Caja>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cajas/{id}")
    public ResponseEntity<Caja> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Caja>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Caja>(HttpStatus.NOT_FOUND);
        }
    }


//    OBTENER ULTIMO REGISTRO DE CAJA
    @GetMapping("/cajas/estatus")
    public ResponseEntity<Caja> getLast() throws Exception {
        try {
            Caja caja = service.obtenerUlimo();
//            if (!caja.toString().contains("null")){
//                return new ResponseEntity<Caja>(caja, HttpStatus.OK);
//            }else{
//                logger.info("NO HAY NINGUNA CAJA REGISTRADA------------------------------------------------>>>>>>>");
//                return null;
//            }
            return new ResponseEntity<Caja>(caja, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Caja>(HttpStatus.NOT_FOUND);
        }
    }
}
