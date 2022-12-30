package com.dev.pc.repository;

import com.dev.pc.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombresContaining(String nombre);
    List<Cliente> findByDniContaining(String dni);
    List<Cliente> findByApepaternoContaining(String ape);
}
