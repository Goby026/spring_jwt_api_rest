package com.dev.pc.services;

import com.dev.pc.models.TipoCliente;
import com.dev.pc.repository.TipoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoClienteService implements DAOService<TipoCliente> {

    @Autowired
    private TipoClienteRepository repository;

    @Override
    public TipoCliente registrar(TipoCliente p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public TipoCliente obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<TipoCliente> listar() throws Exception {
        return repository.findAll();
    }
}
