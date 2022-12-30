package com.dev.pc.services;

import com.dev.pc.models.Tarifario;
import com.dev.pc.repository.TarifarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifarioService implements DAOService<Tarifario> {

    @Autowired
    private TarifarioRepository repository;

    @Override
    public Tarifario registrar(Tarifario p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Tarifario obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Tarifario> listar() throws Exception {
        return repository.findAll();
    }

    public List<Tarifario> listar(Long id) throws Exception {
        return repository.findByServicioCodservi(id);
    }
}
