package com.dev.pc.services;

import com.dev.pc.models.DeudaDescripcion;
import com.dev.pc.repository.DeudaDescripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaDescripcionService implements DAOService<DeudaDescripcion> {

    @Autowired
    private DeudaDescripcionRepository repository;

    @Override
    public DeudaDescripcion registrar(DeudaDescripcion p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public DeudaDescripcion obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<DeudaDescripcion> listar() throws Exception {
        return repository.findAll();
    }
}
