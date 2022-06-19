package com.dev.pc.services;

import com.dev.pc.models.Usuario;
import com.dev.pc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements DAOService<Usuario> {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario registrar(Usuario p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Usuario obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    public Usuario obtenerUsername(String username) throws Exception {
        return repository.findByUsername(username);
    }

    @Override
    public List<Usuario> listar() throws Exception {
        return repository.findAll();
    }
}
