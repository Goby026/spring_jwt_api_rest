package com.dev.pc.repository;

import com.dev.pc.models.TipoPagoServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoServiciosRepository extends JpaRepository<TipoPagoServicios, Long> {
}
