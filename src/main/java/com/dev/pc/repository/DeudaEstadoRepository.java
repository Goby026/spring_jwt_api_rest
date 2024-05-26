package com.dev.pc.repository;

import com.dev.pc.models.DeudaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaEstadoRepository extends JpaRepository<DeudaEstado, Long> {
}
