package com.dev.pc.services;

import com.dev.pc.models.Role;
import com.dev.pc.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements DAOService<Role> {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role registrar(Role p) throws Exception {
        return this.repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Role obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Role> listar() throws Exception {
        return this.repository.findAll();
    }
}
