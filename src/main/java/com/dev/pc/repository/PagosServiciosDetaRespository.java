package com.dev.pc.repository;

import com.dev.pc.models.PagosServiciosDeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosServiciosDetaRespository extends JpaRepository<PagosServiciosDeta, Long> {
    //    implementar buscador por cliente
    List<PagosServiciosDeta> findByClienteIdclientes(Long id);
}
