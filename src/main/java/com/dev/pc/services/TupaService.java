package com.dev.pc.services;

import com.dev.pc.models.Tupa;
import com.dev.pc.repository.TupaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TupaService implements DAOService<Tupa> {

    @Autowired
    private TupaRepository repository;

    @Override
    public Tupa registrar(Tupa p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Tupa obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Tupa> listar() throws Exception {
        return repository.findAll();
    }
}
