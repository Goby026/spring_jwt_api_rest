package com.dev.pc.services;

import com.dev.pc.models.Equipo;
import com.dev.pc.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService implements DAOService<Equipo> {

    @Autowired
    private EquipoRepository repository;

    @Override
    public Equipo registrar(Equipo p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Equipo obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Equipo> listar() throws Exception {
        return this.repository.findAll();
    }
}
