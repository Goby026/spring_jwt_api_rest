package com.dev.pc.services;

import com.dev.pc.models.Tributodetalle;
import com.dev.pc.repository.TributoDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TributoDetalleService implements DAOService<Tributodetalle> {

    @Autowired
    private TributoDetalleRepository repository;

    @Override
    public Tributodetalle registrar(Tributodetalle p) throws Exception {
        return repository.save(p);
    }

    public List<Tributodetalle> registrar(List<Tributodetalle> detalles) throws Exception {
        return repository.saveAll(detalles);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Tributodetalle obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Tributodetalle> listar() throws Exception {
        return repository.findAll();
    }
}
