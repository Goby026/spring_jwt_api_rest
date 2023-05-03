package com.dev.pc.repository;

import com.dev.pc.models.Egreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EgresoRepository extends JpaRepository<Egreso, Long> {
    public List<Egreso> findByCajaIdcaja(long id);
}
