package com.dev.pc.services;

import com.dev.pc.models.Corte;
import com.dev.pc.models.CorteDetalle;
import com.dev.pc.repository.CorteDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorteDetalleService implements DAOService<CorteDetalle> {

    private final CorteDetalleRepository corteDetalleRepository;

    public CorteDetalleService(CorteDetalleRepository corteDetalleRepository) {
        this.corteDetalleRepository = corteDetalleRepository;
    }

    @Override
    public CorteDetalle registrar(CorteDetalle p) throws Exception {
        return this.corteDetalleRepository.save(p);
    }

    public List<CorteDetalle> registrarTodos(List<CorteDetalle> detalles) throws Exception{
        return this.corteDetalleRepository.saveAll(detalles);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.corteDetalleRepository.deleteById(id);
    }

    @Override
    public CorteDetalle obtener(Long id) throws Exception {
        return this.corteDetalleRepository.findById(id).orElseThrow();
    }

    @Override
    public List<CorteDetalle> listar() throws Exception {
        return this.corteDetalleRepository.findAll();
    }

    public List<CorteDetalle> listar(Corte c) throws Exception {
        return this.corteDetalleRepository.findByCorte(c);
    }
}
