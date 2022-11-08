package com.dev.pc.services;

import com.dev.pc.models.Sede;
import com.dev.pc.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeService implements DAOService<Sede> {

    @Autowired
    private SedeRepository repository;

    @Override
    public Sede registrar(Sede p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Sede obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Sede> listar() throws Exception {
        return this.repository.findAll();
    }
}
