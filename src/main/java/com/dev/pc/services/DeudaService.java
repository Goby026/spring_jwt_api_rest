package com.dev.pc.services;

import com.dev.pc.models.Cliente;
import com.dev.pc.models.Deuda;
import com.dev.pc.repository.DeudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaService implements DAOService<Deuda> {

    @Autowired
    private DeudaRepository repository;

    @Override
    public Deuda registrar(Deuda p) throws Exception {
        return repository.save(p);
    }

//    actualizar estado de deudas pagadas
    public List<Deuda> registrar(List<Deuda> deudas) throws Exception {
        return repository.saveAll(deudas);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Deuda obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Deuda> listar() throws Exception {
        return repository.findAll();
    }

    public List<Deuda> listar(Long id) throws Exception {
        return repository.findByClienteIdclientes(id);
    }
}
