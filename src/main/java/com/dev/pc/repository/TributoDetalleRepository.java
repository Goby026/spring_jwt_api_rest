package com.dev.pc.repository;

import com.dev.pc.models.Tributodetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TributoDetalleRepository extends JpaRepository<Tributodetalle, Long> {
}
