package com.dev.pc.controllers;

import com.dev.pc.models.*;
import com.dev.pc.services.*;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class DeudaController {

    private static final Logger logger = LoggerFactory.getLogger(DeudaController.class);

    private DeudaService service;
    private DeudaDescripcionService deudaDescripcionService;
    private DeudaEstadoService deudaEstadoService;
    private PagosServicioService pagosServicioService;
    private ClienteService clienteService;
    private CostootroservicioService costootroservicioService;

    public DeudaController(DeudaService service, DeudaDescripcionService deudaDescripcionService, DeudaEstadoService deudaEstadoService, PagosServicioService pagosServicioService, ClienteService clienteService, TarifarioService tarifarioService, CostootroservicioService costootroservicioService) {
        this.service = service;
        this.deudaDescripcionService = deudaDescripcionService;
        this.deudaEstadoService = deudaEstadoService;
        this.pagosServicioService = pagosServicioService;
        this.clienteService = clienteService;
        this.costootroservicioService = costootroservicioService;
    }

    @GetMapping("/deudas")
    public ResponseEntity<HashMap<String, List<Deuda>>> list() throws Exception {
        List<Deuda> deudas = this.service.listar();
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* =========BUSCAR DEUDAS POR ID DE CLIENTE========= */
    @GetMapping("/deudas/buscar/{id}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listar(@PathVariable(value = "id") Long id) throws Exception {
        List<Deuda> deudas = this.service.listar(id);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* =========BUSCAR DEUDAS DE ZONA POR RANGO DE FECHAS========= */
    @GetMapping("/deudas/buscar-zona/{idzona}/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorZonaAnnio(@PathVariable(value = "idzona") Long idzona,
                                                                           @PathVariable(value = "desde") String desde,
                                                                           @PathVariable(value = "hasta") String hasta) throws Exception {

        Date dateInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Deuda> deudas = this.service.listarPorZonaAnnio(idzona, dateInicio, dateFin);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* =========BUSCAR DEUDAS POR PERIODO========= */
    @GetMapping("/deudas/buscar-periodo/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorRangoFechas(@PathVariable(value = "desde") String desde,
                                                                           @PathVariable(value = "hasta") String hasta) throws Exception {

        Date dateInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Deuda> deudas = this.service.listarPorPeriodos(dateInicio, dateFin);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* =========BUSCAR DEUDAS DE CLIENTE POR RANGO DE FECHAS========= */
    @GetMapping("/deudas/buscar-cliente/{idcliente}/{desde}/{hasta}")
    public ResponseEntity<HashMap<String, List<Deuda>>> listarPorClienteAnnio(@PathVariable(value = "idcliente") Long idcliente,
                                                                              @PathVariable(value = "desde") String desde,
                                                                              @PathVariable(value = "hasta") String hasta) throws Exception {

        Date dateInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        List<Deuda> deudas = this.service.listarPorClienteAnnio(idcliente, dateInicio, dateFin);
        HashMap<String, List<Deuda>> resp = new HashMap<>();
        resp.put("deudas", deudas);
        return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
    }

    /* =========DEUDAS POR ZONA========= */
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

    /* =========DEUDAS POR ZONA========= */
    @PostMapping("/deudas")
    public ResponseEntity<Deuda> add(@RequestBody Deuda d) throws Exception {
        try {
            SimpleDateFormat standar = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = standar.format(d.getPeriodo());
            Date calDate = standar.parse(dateStr);
            d.setPeriodo(calDate);
            Deuda deuda = service.registrar(d);
            return new ResponseEntity<Deuda>(d, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deudas-all")
    public ResponseEntity<HashMap<String, List<Deuda>>> saveAll(@RequestBody List<Deuda> d) throws Exception {
        try {
            List<Deuda> deudas = service.registrarTodo(d);
            HashMap<String, List<Deuda>> resp = new HashMap<>();
            resp.put("deudas", deudas);
            return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<Deuda>>>(HttpStatus.NO_CONTENT);
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

    @PutMapping("/deuda/{id}")
    public ResponseEntity<Deuda> update(@RequestBody Deuda deuda, @PathVariable Long id) throws Exception {
        try {
            service.registrar(deuda);
            return new ResponseEntity<Deuda>(deuda, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Deuda>(HttpStatus.NOT_FOUND);
        }
    }

    //    api rest para actualizar estado de las deudas
    @PreAuthorize("permitAll()")
    @PutMapping("/deudas/service")
    public ResponseEntity<HashMap<String, List<Deuda>>> update(@RequestBody List<Deuda> d) throws Exception {
        try {
            List<Deuda> deudas = service.registrar(d);
            HashMap<String, List<Deuda>> resp = new HashMap<>();
            resp.put("deudas", deudas);
            return new ResponseEntity<HashMap<String, List<Deuda>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
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

    @GetMapping("/generar-deudas")
    public ResponseEntity<HashMap<String,List<Deuda>>> addDeudas() throws Exception {
        try {

            List<Cliente> clientes = clienteService.listar();
            String fecha = DateTimeFormatter.ofPattern("yyyy-MM").format(LocalDate.now());
            List<PagosServicio> pagosServicios =  pagosServicioService.listarPorYearMonth(fecha);

//            separamos el id de los pagos realizados en una nueva lista
            Set<Long> idClientesA = new HashSet<>();

            HashMap<String, List<Deuda>> resp = new HashMap<>();
            List<Deuda> deudas = new ArrayList<>();

            DeudaDescripcion deudaDescripcion = deudaDescripcionService.obtener(1L);
            DeudaEstado deudaEstado = deudaEstadoService.obtener(3L);

            if (!pagosServicios.isEmpty()){

                for (PagosServicio pago: pagosServicios){
                    if (pago.getTipoPagoServicios().getIdtipopagosservicio() == 3 && pago.getPagoServicioEstado().getIdpagoestado()==1){
                        idClientesA.add(pago.getCliente().getIdclientes());
                    }
                }

                List<Cliente> clientesFiltrados = clientes.stream()
                        .filter( clienteB -> !idClientesA.contains(clienteB.getIdclientes()))
                        .collect(Collectors.toList());


                logger.info("PAGOS------->" + pagosServicios);

                logger.info("CLIENTES FILTRADOS-----> " + clientesFiltrados);

            for (Cliente cli : clientesFiltrados){

                Deuda deuda = new Deuda();

                deuda.setCliente(cli);
                deuda.setCodigo("1");
                deuda.setDeudaDescripcion(deudaDescripcion);
                deuda.setDeudaEstado(deudaEstado);
                deuda.setPeriodo(new Date());
                if (this.costootroservicioService.obtener(cli.getIdclientes())!=null){
                    deuda.setSaldo(this.costootroservicioService.obtener(cli.getIdclientes()).getTarifario().getMonto());
                }else{
                    deuda.setSaldo(0);
                }
                deuda.setTotal(deuda.getSaldo());
                deuda.setDcto(0);
                deuda.setVencimiento(new Date());
                deuda.setEstado(1);
                deuda.setObservacion("Generado automáticamente por corte");

                deudas.add(deuda);
            }
                resp.put("deudas", deudas);
            }else {

            for (Cliente cli : clientes){

                Deuda deuda = new Deuda();

                deuda.setCliente(cli);
                deuda.setCodigo("1");
                deuda.setDeudaDescripcion(deudaDescripcion);
                deuda.setDeudaEstado(deudaEstado);
                deuda.setPeriodo(new Date());
                if (this.costootroservicioService.obtener(cli.getIdclientes())!=null){
                    deuda.setSaldo(this.costootroservicioService.obtener(cli.getIdclientes()).getTarifario().getMonto());
                }else{
                    deuda.setSaldo(0);
                }
                deuda.setTotal(deuda.getSaldo());
                deuda.setDcto(0);
                deuda.setVencimiento(new Date());
                deuda.setEstado(1);
                deuda.setObservacion("Generado automáticamente por corte");

                deudas.add(deuda);
            }
                resp.put("deudas", deudas);
            }

            return new ResponseEntity<HashMap<String,List<Deuda>>>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String,List<Deuda>>>(HttpStatus.NO_CONTENT);
        }
    }

}
