package com.dev.pc.repository;

import com.dev.pc.models.Tupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TupaRepository extends JpaRepository<Tupa, Long> {
}
