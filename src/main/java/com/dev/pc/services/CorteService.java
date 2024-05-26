package com.dev.pc.services;

import com.dev.pc.models.Corte;
import com.dev.pc.repository.CorteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorteService implements DAOService<Corte>{

    CorteRepository corteRepository;

    public CorteService(CorteRepository corteRepository) {
        this.corteRepository = corteRepository;
    }

    @Override
    public Corte registrar(Corte p) throws Exception {
        return this.corteRepository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.corteRepository.deleteById(id);
    }

    @Override
    public Corte obtener(Long id) throws Exception {
        return this.corteRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Corte> listar() throws Exception {
        return this.corteRepository.findAll();
    }
}
