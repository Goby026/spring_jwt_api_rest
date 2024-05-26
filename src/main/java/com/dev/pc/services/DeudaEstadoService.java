package com.dev.pc.services;

import com.dev.pc.models.DeudaEstado;
import com.dev.pc.repository.DeudaEstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaEstadoService implements DAOService<DeudaEstado> {

    DeudaEstadoRepository deudaEstadoRepository;

    public DeudaEstadoService(DeudaEstadoRepository deudaEstadoRepository) {
        this.deudaEstadoRepository = deudaEstadoRepository;
    }

    @Override
    public DeudaEstado registrar(DeudaEstado p) throws Exception {
        return this.deudaEstadoRepository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.deudaEstadoRepository.deleteById(id);
    }

    @Override
    public DeudaEstado obtener(Long id) throws Exception {
        return this.deudaEstadoRepository.findById(id).orElseThrow();
    }

    @Override
    public List<DeudaEstado> listar() throws Exception {
        return this.deudaEstadoRepository.findAll();
    }
}
