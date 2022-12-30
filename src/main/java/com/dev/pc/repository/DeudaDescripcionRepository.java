package com.dev.pc.repository;

import com.dev.pc.models.DeudaDescripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaDescripcionRepository extends JpaRepository<DeudaDescripcion, Long> {
}
