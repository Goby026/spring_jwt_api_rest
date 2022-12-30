package com.dev.pc.services;

import com.dev.pc.models.TipoPagoServicios;
import com.dev.pc.repository.TipoPagoServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagoServiciosService implements DAOService<TipoPagoServicios>{

    @Autowired
    private TipoPagoServiciosRepository repository;

    @Override
    public TipoPagoServicios registrar(TipoPagoServicios p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public TipoPagoServicios obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<TipoPagoServicios> listar() throws Exception {
        return repository.findAll();
    }
}
