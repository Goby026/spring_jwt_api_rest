package com.dev.pc.services;

import com.dev.pc.models.Servicio;
import com.dev.pc.repository.ServicioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService implements DAOService<Servicio> {

    @Autowired
    private ServicioRespository respository;

    @Override
    public Servicio registrar(Servicio p) throws Exception {
        return respository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        respository.deleteById(id);
    }

    @Override
    public Servicio obtener(Long id) throws Exception {
        return respository.findById(id).get();
    }

    @Override
    public List<Servicio> listar() throws Exception {
        return respository.findAll();
    }
}
