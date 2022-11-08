package com.dev.pc.services;

import com.dev.pc.models.UsuarioEquipo;
import com.dev.pc.repository.UsuarioEquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioEquipoService implements DAOService<UsuarioEquipo> {

    @Autowired
    private UsuarioEquipoRepository repository;

    @Override
    public UsuarioEquipo registrar(UsuarioEquipo p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public UsuarioEquipo obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<UsuarioEquipo> listar() throws Exception {
        return this.repository.findAll();
    }
}
