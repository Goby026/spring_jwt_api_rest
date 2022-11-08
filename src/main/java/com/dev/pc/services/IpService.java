package com.dev.pc.services;

import com.dev.pc.models.Ip;
import com.dev.pc.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpService implements DAOService<Ip> {

    @Autowired
    private IpRepository repository;

    @Override
    public Ip registrar(Ip p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Ip obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Ip> listar() throws Exception {
        return this.repository.findAll();
    }
}
