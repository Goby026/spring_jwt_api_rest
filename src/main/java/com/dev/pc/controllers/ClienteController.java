package com.dev.pc.controllers;

import com.dev.pc.models.Cliente;
import com.dev.pc.services.ClienteService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
//@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService service;

    @GetMapping("/clientes")
    public ResponseEntity<HashMap<String, List<Cliente>>> list() throws Exception {
        List<Cliente> clientes = this.service.listar();
        HashMap<String, List<Cliente>> resp = new HashMap<>();
        resp.put("clientes", clientes);
        return new ResponseEntity<HashMap<String, List<Cliente>>>(resp, HttpStatus.OK);

    }

    @GetMapping("/clientes/listar/{nombre}")
    public ResponseEntity<HashMap<String, List<Cliente>>> listar(@PathVariable String nombre) throws Exception {
        List<Cliente> clientes = this.service.buscarClientePorNombre(nombre);
        HashMap<String, List<Cliente>> resp = new HashMap<>();
        resp.put("clientes", clientes);
        return new ResponseEntity<HashMap<String, List<Cliente>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/clientes/buscar")
    public ResponseEntity<HashMap<String, List<Cliente>>> listar(@RequestParam String nombre,
                                                                 @RequestParam String dni,
                                                                 @RequestParam String ape) throws Exception {

        List<Cliente> clientes = null;

        if (!nombre.isEmpty()) {
            clientes = this.service.buscarClientePorNombre(nombre);
        } else if (!dni.isEmpty()) {
            clientes = this.service.buscarClientePorDni(dni);
        } else {
            clientes = this.service.buscarClientePorApellido(ape);
        }

        HashMap<String, List<Cliente>> resp = new HashMap<>();
        resp.put("clientes", clientes);
        return new ResponseEntity<HashMap<String, List<Cliente>>>(resp, HttpStatus.OK);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) throws Exception {
        HashMap<String, Object> resp= new HashMap<>();
        try {
            Cliente cliente = service.obtener(id);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        } catch (DataAccessException e) {
            resp.put("msg", "Error al realizar la consulta en la base de datos");
            resp.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<HashMap<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException ne){
            resp.put("msg",
                    "El cliente con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            resp.put("Error", ne.getMessage());
            return new ResponseEntity<HashMap<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }catch (MalformedJwtException e){
            resp.put("Error", e.getMessage());
            return new ResponseEntity<HashMap<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> add(@RequestBody Cliente c) throws Exception {
        try {
            Cliente cliente = service.registrar(c);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cliente>(HttpStatus.BAD_REQUEST);
        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> update(@RequestBody Cliente c, @PathVariable Long id) throws Exception {
        try {
            service.registrar(c);
            return new ResponseEntity<Cliente>(c, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable Long id) throws Exception {
        try {
            service.eliminar(id);
            return new ResponseEntity<Cliente>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/clientes/name")
    public ResponseEntity<HashMap<String, List<Cliente>>> getByNombre(@RequestParam String nombre) throws Exception {

        List<Cliente> clientes = this.service.buscarClientePorNombre(nombre);
        HashMap<String, List<Cliente>> resp = new HashMap<>();
        resp.put("clientes", clientes);
        return new ResponseEntity<HashMap<String, List<Cliente>>>(resp, HttpStatus.OK);

    }
}
