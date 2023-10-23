package com.dev.pc.services;

import com.dev.pc.models.Ingreso;
import com.dev.pc.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService implements DAOService<Ingreso> {

    @Autowired
    private IngresoRepository repository;

    @Override
    public Ingreso registrar(Ingreso p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Ingreso obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Ingreso> listar() throws Exception {
        return repository.findAll();
    }
}
