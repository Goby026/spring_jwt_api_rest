package com.dev.pc.repository;

import com.dev.pc.models.TipoEgreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEgresoRepository extends JpaRepository<TipoEgreso, Long> {
}
