package com.dev.pc.services;

import com.dev.pc.models.Voucher;
import com.dev.pc.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService implements DAOService<Voucher> {

    @Autowired
    private VoucherRepository repository;

    @Override
    public Voucher registrar(Voucher p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Voucher obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Voucher> listar() throws Exception {
        return repository.findAll();
    }

    public List<Voucher> listar(String nombres) throws Exception {
        return repository.findByClienteNombresContaining(nombres);
    }
}
