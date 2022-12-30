package com.dev.pc.repository;

import com.dev.pc.models.Requisitos;
import com.dev.pc.models.Tupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitosRepository extends JpaRepository<Requisitos, Long> {
    List<Requisitos> findByTupa(Tupa tupa);
}
