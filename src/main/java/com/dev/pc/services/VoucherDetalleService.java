package com.dev.pc.services;

import com.dev.pc.models.VoucherDetalle;
import com.dev.pc.repository.VoucherDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherDetalleService implements DAOService<VoucherDetalle> {

    @Autowired
    private VoucherDetalleRepository repository;

    @Override
    public VoucherDetalle registrar(VoucherDetalle p) throws Exception {
        return repository.save(p);
    }

    public List<VoucherDetalle> registrarTodos(List<VoucherDetalle> detalles) throws Exception {
        return repository.saveAll(detalles);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public VoucherDetalle obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<VoucherDetalle> listar() throws Exception {
        return repository.findAll();
    }

    public List<VoucherDetalle> listar(Long idvoucher) throws Exception {
        return repository.findByVoucherIdvoucher(idvoucher);
    }

    public List<VoucherDetalle> listarPorCliente(Long idCliente) throws Exception {
        return repository.findByVoucherClienteIdclientes(idCliente);
    }
}
