package com.dev.pc.repository;

import com.dev.pc.models.Tarifario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifarioRepository extends JpaRepository<Tarifario, Long> {
    List<Tarifario> findByServicioCodservi(Long id);
}
