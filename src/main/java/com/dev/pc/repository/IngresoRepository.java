package com.dev.pc.repository;

import com.dev.pc.models.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
