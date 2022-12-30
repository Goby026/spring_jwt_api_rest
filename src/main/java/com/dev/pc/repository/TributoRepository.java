package com.dev.pc.repository;

import com.dev.pc.models.Tributo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TributoRepository extends JpaRepository<Tributo, Long> {
    long count();
}
