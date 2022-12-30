package com.dev.pc.services;

import com.dev.pc.models.Tributo;
import com.dev.pc.repository.TributoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TributoService implements DAOService<Tributo> {

    @Autowired
    private TributoRepository repository;

    @Override
    public Tributo registrar(Tributo p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Tributo obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Tributo> listar() throws Exception {
        return repository.findAll();
    }

    public int contar() throws Exception {
        return (int)repository.count();
    }
}
