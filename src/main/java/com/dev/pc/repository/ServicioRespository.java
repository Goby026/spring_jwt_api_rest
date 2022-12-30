package com.dev.pc.repository;

import com.dev.pc.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRespository extends JpaRepository<Servicio, Long> {
}
