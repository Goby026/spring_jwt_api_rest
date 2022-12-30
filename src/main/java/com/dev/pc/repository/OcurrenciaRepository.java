package com.dev.pc.repository;

import com.dev.pc.models.Ocurrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcurrenciaRepository extends JpaRepository<Ocurrencia, Long> {
}
