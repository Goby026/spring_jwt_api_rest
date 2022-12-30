package com.dev.pc.repository;

import com.dev.pc.models.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    public List<Caja> findByOrderByIdcajaDesc();

    public Caja findTopByOrderByIdcajaDesc();

    @Query("from Caja c where DATE(c.fapertura) = :date")
    public List<Caja> findByFapertura(@Param("date") Date date);

}
