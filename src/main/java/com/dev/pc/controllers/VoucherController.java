package com.dev.pc.controllers;

import com.dev.pc.models.Voucher;
import com.dev.pc.models.VoucherDetalle;
import com.dev.pc.services.VoucherDetalleService;
import com.dev.pc.services.VoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class VoucherController {
    
    @Autowired
    private VoucherService service;

    @Autowired
    private VoucherDetalleService detalleService;

    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @GetMapping("/vouchers")
    public ResponseEntity<HashMap<String, List<Voucher>>> list() throws Exception {
        List<Voucher> vouchers = this.service.listar();
        HashMap<String, List<Voucher>> resp = new HashMap<>();
        resp.put("vouchers", vouchers);
        return new ResponseEntity<HashMap<String, List<Voucher>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/vouchers/search/{nombres}")
    public ResponseEntity<HashMap<String, List<Voucher>>> list(@PathVariable(value = "nombres") String nombres) throws Exception {

        try {

            List<Voucher> vouchers = this.service.listar(nombres);
            HashMap<String, List<Voucher>> resp = new HashMap<>();
            resp.put("vouchers", vouchers);

            return new ResponseEntity<HashMap<String, List<Voucher>>>(resp, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<HashMap<String, List<Voucher>>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/vouchers/{id}")
    public ResponseEntity<Voucher> get(@PathVariable(value = "id") Long id) throws Exception {
        try {

            Voucher c = service.obtener(id);

            return new ResponseEntity<Voucher>(c, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Voucher>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/vouchers")
    public ResponseEntity<Voucher> add(@RequestBody Voucher v) throws Exception {
        try {
            Voucher voucher = service.registrar(v);

            return new ResponseEntity<Voucher>(voucher, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Voucher>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/vouchers/save")
    public ResponseEntity<?> add(@RequestParam("voucher") String v,
                                 @RequestParam("detalles") String d) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        Voucher voucher = mapper.readValue(v, Voucher.class);

        VoucherDetalle[] detalles = mapper.readValue(d, VoucherDetalle[].class);
        List<VoucherDetalle> details = new ArrayList<>();
        for (VoucherDetalle detalle : detalles) {
            details.add(detalle);
        }

        Voucher voucherSaved = service.registrar(voucher);

        details.stream().forEach( (det)->{
            det.setVoucher(voucherSaved);
        });

        detalleService.registrarTodos(details);
        response.put("voucher", voucher);
        response.put("detalles", details);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

//    subir imagen
    @PreAuthorize("permitAll()")
    @PostMapping("/vouchers/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,
                                    @RequestParam("id") Long id) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();
        Voucher voucher = service.obtener(id);

        if(!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
//            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
            Path rutaArchivo = Paths.get("/opt/uploads").resolve(nombreArchivo).toAbsolutePath();
            logger.info(rutaArchivo.toString());
            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("message",  "Error al subir la imagen del voucher" + nombreArchivo );
                response.put("error",  e.getMessage().concat(": ").concat(e.getCause().getMessage())) ;
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nomImagenAnterior = voucher.getImagen();

            if (nomImagenAnterior != null && nomImagenAnterior.length() > 0){
//                Path rutaImagenAnterior = Paths.get("uploads").resolve(nomImagenAnterior).toAbsolutePath();
                Path rutaImagenAnterior = Paths.get("/opt/uploads").resolve(nomImagenAnterior).toAbsolutePath();
                File archivoImagenAnterior = rutaImagenAnterior.toFile();
                if (archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()){
                    archivoImagenAnterior.delete();
                }
            }

            voucher.setImagen(nombreArchivo);

            service.registrar(voucher);

            response.put("voucher",  voucher);
            response.put("mensaje",  "Se subio correctamente la imagen del voucher" + nombreArchivo);


        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

//    controlador para mostrar imagen
    @GetMapping("/uploads/img/{nombreImagen:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen){

//        Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
        Path rutaArchivo = Paths.get("/opt/uploads").resolve(nombreImagen).toAbsolutePath();
        Resource recurso= null;
        logger.info(rutaArchivo.toString());

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!recurso.exists() && recurso.isReadable()){
            throw new RuntimeException("No se pudo cargar la imagen "+ nombreImagen);
        }

        HttpHeaders cabeceras = new HttpHeaders();

        cabeceras.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename()+ "\"");

        return new ResponseEntity<Resource>(recurso, cabeceras, HttpStatus.OK);

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/vouchers/{id}")
    public ResponseEntity<Voucher> update(@RequestBody Voucher c, @PathVariable Long id) throws Exception {
        try {
            Voucher voucher = service.registrar(c);
            return new ResponseEntity<Voucher>(voucher, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Voucher>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Voucher> delete(@PathVariable Long id) throws Exception {
        try {
            Voucher voucher = service.obtener(id);
            String nomImagenAnterior = voucher.getImagen();

            if (nomImagenAnterior != null && nomImagenAnterior.length() > 0){
//                Path rutaImagenAnterior = Paths.get("uploads").resolve(nomImagenAnterior).toAbsolutePath();
                Path rutaImagenAnterior = Paths.get("/opt/uploads").resolve(nomImagenAnterior).toAbsolutePath();
                File archivoImagenAnterior = rutaImagenAnterior.toFile();
                if (archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()){
                    archivoImagenAnterior.delete();
                }
            }
            service.eliminar(id);
            return new ResponseEntity<Voucher>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Voucher>(HttpStatus.NOT_FOUND);
        }
    }

}
