package com.dev.pc.services;

import com.dev.pc.models.Year;
import com.dev.pc.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearService implements DAOService<Year> {

    @Autowired
    private YearRepository repository;

    @Override
    public Year registrar(Year p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Year obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Year> listar() throws Exception {
        return repository.findAll();
    }
}
