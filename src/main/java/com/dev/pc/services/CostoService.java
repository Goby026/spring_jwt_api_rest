package com.dev.pc.services;

import com.dev.pc.models.Cliente;
import com.dev.pc.models.Costo;
import com.dev.pc.repository.CostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostoService implements DAOService<Costo>{

    @Autowired
    private CostoRepository repository;

    @Override
    public Costo registrar(Costo p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Costo obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Costo> listar() throws Exception {
        return repository.findAll();
    }

    public List<Costo> listar(Long id) throws Exception {
        return repository.findByClienteIdclientes(id);
    }
}
