package com.dev.pc.controllers;

import com.dev.pc.models.UsuarioEquipo;
import com.dev.pc.services.UsuarioEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class UsuarioEquipoController {

    @Autowired
    private UsuarioEquipoService service;

    @GetMapping("/usuario-equipos")
    public List<UsuarioEquipo> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/usuario-equipos/{id}")
    public ResponseEntity<UsuarioEquipo> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {

            UsuarioEquipo ue = service.obtener(id);

            return new ResponseEntity<UsuarioEquipo>(ue, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<UsuarioEquipo>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuario-equipos")
    public ResponseEntity<UsuarioEquipo> add( @RequestBody UsuarioEquipo ue) throws Exception {

        try {

            UsuarioEquipo usuarioEquipo = service.registrar(ue);

            return new ResponseEntity<UsuarioEquipo>(usuarioEquipo, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<UsuarioEquipo>(HttpStatus.BAD_REQUEST);

        }

    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/usuario-equipos/{id}")
    public ResponseEntity<UsuarioEquipo> update( @RequestBody UsuarioEquipo ue ,@PathVariable Long id ) throws Exception{
        try {
//            Marca marca = service.obtener(id);

            service.registrar(ue);

            return new ResponseEntity<UsuarioEquipo>(ue,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<UsuarioEquipo>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/usuario-equipos/{id}")
    public ResponseEntity<UsuarioEquipo> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<UsuarioEquipo>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UsuarioEquipo>(HttpStatus.NOT_FOUND);
        }
    }

}
