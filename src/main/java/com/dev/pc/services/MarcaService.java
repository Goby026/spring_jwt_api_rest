package com.dev.pc.services;

import com.dev.pc.models.Marca;
import com.dev.pc.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService implements DAOService<Marca>{

    @Autowired
    private MarcaRepository repository;

    @Override
    public Marca registrar(Marca p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Marca obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Marca> listar() throws Exception {
        return this.repository.findAll();
    }
}
