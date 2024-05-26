package com.dev.pc.repository;

import com.dev.pc.models.Corte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorteRepository extends JpaRepository<Corte, Long> {
}
