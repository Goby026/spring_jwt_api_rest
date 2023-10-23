package com.dev.pc.services;

import com.dev.pc.models.Cliente;
import com.dev.pc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements DAOService<Cliente> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public Cliente registrar(Cliente p) throws Exception {
        return repository.save(p);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Cliente obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Cliente> listar() throws Exception {
        return repository.findAll();
    }

    public List<Cliente> listar(String valor) throws Exception {
        if (valor != null){
            return repository.findAll(valor);
        }
        return repository.findAll();
    }

    public List<Cliente> buscarClientePorNombre(String nombre) throws Exception {
        return repository.findByNombresContaining(nombre);
    }

    public List<Cliente> buscarClientePorDni(String dni) throws Exception {
        return repository.findByDniContaining(dni);
    }

    public Cliente buscarPorDni(String dni) throws Exception {
        return repository.findByDni(dni);
    }

    public List<Cliente> buscarClientePorApellido(String ape) throws Exception {
        return repository.findByApepaternoContaining(ape);
    }

    public List<Cliente> buscarClientesPorZona(Long idzona) throws Exception{
        return repository.findByZonaIdtbzonas(idzona);
    }

//    public List<Cliente> buscarTodo(String valor) throws Exception{
//
//        if (valor != null){
//            return repository.searchAll(valor);
//        }
//        return repository.findAll();
//    }
}
