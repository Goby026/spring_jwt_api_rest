package com.dev.pc.services;

import com.dev.pc.models.Tarifario;
import com.dev.pc.repository.TarifarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifarioService implements DAOService<Tarifario> {


    private final TarifarioRepository repository;

    public TarifarioService(TarifarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tarifario registrar(Tarifario p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Tarifario obtener(Long id) throws Exception {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<Tarifario> listar() throws Exception {
        return this.repository.findAll();
    }

    public List<Tarifario> listar(Long id) throws Exception {
        return this.repository.findByServicioCodservi(id);
    }
}
