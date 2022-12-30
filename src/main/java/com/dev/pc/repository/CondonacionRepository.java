package com.dev.pc.repository;

import com.dev.pc.models.Condonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondonacionRepository extends JpaRepository<Condonacion, Long> {
}
