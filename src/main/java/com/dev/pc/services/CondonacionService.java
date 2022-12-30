package com.dev.pc.services;

import com.dev.pc.models.Condonacion;
import com.dev.pc.repository.CondonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondonacionService implements DAOService<Condonacion> {

    @Autowired
    private CondonacionRepository repository;

    @Override
    public Condonacion registrar(Condonacion p) throws Exception {
        return repository.save(p);
    }

    public List<Condonacion> registrar(List<Condonacion> condonacions) throws Exception {
        return repository.saveAll(condonacions);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Condonacion obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Condonacion> listar() throws Exception {
        return repository.findAll();
    }
}
