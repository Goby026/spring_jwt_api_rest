package com.dev.pc.repository;

import com.dev.pc.models.Costootroservicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostootroservicioRepository extends JpaRepository<Costootroservicio, Long> {
    List<Costootroservicio> findByCostoCodcosto(Long id);
}
