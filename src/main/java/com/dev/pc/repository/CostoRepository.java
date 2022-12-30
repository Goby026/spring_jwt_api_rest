package com.dev.pc.repository;

import com.dev.pc.models.Cliente;
import com.dev.pc.models.Costo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostoRepository extends JpaRepository<Costo, Long> {
    List<Costo> findByClienteIdclientes(Long id);
}
