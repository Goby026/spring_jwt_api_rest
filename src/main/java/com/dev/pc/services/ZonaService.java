package com.dev.pc.services;

import com.dev.pc.models.Zona;
import com.dev.pc.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaService implements DAOService<Zona> {

    @Autowired
    private ZonaRepository repository;

    @Override
    public Zona registrar(Zona p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Zona obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Zona> listar() throws Exception {
        return repository.findAll();
    }
}
