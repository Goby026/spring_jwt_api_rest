package com.dev.pc.services;

import com.dev.pc.models.TipoComponente;
import com.dev.pc.repository.TipoComponenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoComponenteService implements DAOService<TipoComponente> {

    @Autowired
    private TipoComponenteRepository repository;

    @Override
    public TipoComponente registrar(TipoComponente p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public TipoComponente obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<TipoComponente> listar() throws Exception {
        return this.repository.findAll();
    }
}
