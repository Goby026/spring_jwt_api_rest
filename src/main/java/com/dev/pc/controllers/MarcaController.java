package com.dev.pc.controllers;

import com.dev.pc.models.Marca;
import com.dev.pc.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping("/marcas")
    public ResponseEntity<HashMap<String, List<Marca>>> list() throws Exception {
        List<Marca> marcas = this.service.listar();
        HashMap<String, List<Marca>> resp = new HashMap<>();
        resp.put("marcas", marcas);
        return new ResponseEntity<HashMap<String, List<Marca>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/marcas/{id}")
    public ResponseEntity<Marca> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Marca m = service.obtener(id);

            return new ResponseEntity<Marca>(m, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Marca>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/marcas")
    public ResponseEntity<Marca> add( @RequestBody Marca m) throws Exception {

        try {

            Marca marca = service.registrar(m);

            return new ResponseEntity<Marca>(marca, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Marca>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/marcas/{id}")
    public ResponseEntity<Marca> update( @RequestBody Marca m ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(m);

            return new ResponseEntity<Marca>(m,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Marca>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<Marca> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Marca>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Marca>(HttpStatus.NOT_FOUND);
        }
    }

}
