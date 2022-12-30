package com.dev.pc.controllers;


import com.dev.pc.models.Role;
import com.dev.pc.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("/roles")
    public List<Role> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            Role role = service.obtener(id);

            return new ResponseEntity<Role>(role, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> add( @RequestBody Role req_role) throws Exception {

        try {

            Role role = service.registrar(req_role);

            return new ResponseEntity<Role>(role, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> update( @RequestBody Role req_role ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(req_role);

            return new ResponseEntity<Role>(req_role,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Role> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Role>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }
    }

}
