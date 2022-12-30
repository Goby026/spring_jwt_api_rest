package com.dev.pc.controllers;

import com.dev.pc.models.Usuario;
import com.dev.pc.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api/v1")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService service;


    @GetMapping("/usuarios")
    public List<Usuario> list() throws Exception {
        return this.service.listar();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> get(@PathVariable(value = "id") Long id) throws Exception  {
        try {
            Usuario usuario = service.obtener(id);

            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
    }

//    obtener por nombre de usuario
    @GetMapping("/usuarios/{username}")
    public ResponseEntity<Usuario> get(@PathVariable(value = "username") String u) throws Exception  {
        try {
            Usuario usuario = service.obtenerUsername(u);

            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<Usuario> getUser(@PathVariable(value = "username") String u) throws Exception  {
        try {
            Usuario usuario = service.obtenerUsername(u);
            usuario.setPassword("");

            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> add(@RequestBody Usuario usuario) throws Exception {
        try {

            Usuario user = service.registrar(usuario);

            return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        }
    }

    //se debe enviar el objeto completo, incluyendo el id, de lo contrario creará un registro nuevo
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> update(@RequestBody Usuario u , @PathVariable Long id ) throws Exception{
        try {

            service.registrar(u);

            return new ResponseEntity<Usuario>(u,HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> delete( @PathVariable Long id) throws Exception{
        try {
            service.eliminar(id);
            return new ResponseEntity<Usuario>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
    }
}
