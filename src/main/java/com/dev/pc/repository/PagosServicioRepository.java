package com.dev.pc.repository;

import com.dev.pc.models.PagosServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosServicioRepository extends JpaRepository<PagosServicio, Long> {
    List<PagosServicio> findByClienteIdclientes( Long id );
    List<PagosServicio> findByCajaIdcaja( Long idcaja );
    long count();
}
