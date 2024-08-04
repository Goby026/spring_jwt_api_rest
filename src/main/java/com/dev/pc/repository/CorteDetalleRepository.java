package com.dev.pc.repository;

import com.dev.pc.models.Corte;
import com.dev.pc.models.CorteDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorteDetalleRepository extends JpaRepository<CorteDetalle,Long> {
    List<CorteDetalle> findByCorte(Corte c);
}
