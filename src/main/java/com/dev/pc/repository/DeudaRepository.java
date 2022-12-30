package com.dev.pc.repository;

import com.dev.pc.models.Cliente;
import com.dev.pc.models.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {
    List<Deuda> findByClienteIdclientes(Long id);
}
