package com.dev.pc.services;

import com.dev.pc.models.Requisitos;
import com.dev.pc.models.Tupa;
import com.dev.pc.repository.RequisitosRepository;
import com.dev.pc.repository.TarifarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequisitosService implements DAOService<Requisitos> {

    @Autowired
    private RequisitosRepository repository;

    @Override
    public Requisitos registrar(Requisitos p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Requisitos obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Requisitos> listar() throws Exception {
        return repository.findAll();
    }

    public List<Requisitos> listarPorTupa(Tupa tupa) throws Exception {
        return repository.findByTupa(tupa);
    }
}
