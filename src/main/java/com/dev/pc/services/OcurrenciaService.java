package com.dev.pc.services;

import com.dev.pc.models.Ocurrencia;
import com.dev.pc.repository.OcurrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcurrenciaService implements DAOService<Ocurrencia> {

    @Autowired
    private OcurrenciaRepository repository;

    @Override
    public Ocurrencia registrar(Ocurrencia p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Ocurrencia obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Ocurrencia> listar() throws Exception {
        return repository.findAll();
    }
}
