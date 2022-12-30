package com.dev.pc.services;

import com.dev.pc.models.PagosServicio;
import com.dev.pc.repository.PagosServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagosServicioService implements DAOService<PagosServicio>{

    @Autowired
    private PagosServicioRepository repository;

    @Override
    public PagosServicio registrar(PagosServicio p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public PagosServicio obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<PagosServicio> listar() throws Exception {
        return repository.findAll();
    }

    public List<PagosServicio> listar(Long id) throws Exception {
        return repository.findByClienteIdclientes(id);
    }

    public int contar() throws Exception {
        return  (int)repository.count();
    }

    public List<PagosServicio> listarPorCaja(Long idcaja) throws Exception {
        return repository.findByCajaIdcaja(idcaja);
    }
}
