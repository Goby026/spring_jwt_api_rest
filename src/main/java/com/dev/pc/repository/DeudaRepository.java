package com.dev.pc.repository;

import com.dev.pc.models.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {
    List<Deuda> findByClienteIdclientes(Long id);
    List<Deuda> findByClienteZonaIdtbzonasAndPeriodoBetween(Long idzona, Date inicio, Date fin);
    List<Deuda> findByClienteIdclientesAndPeriodoBetween(Long idCliente, Date inicio, Date fin);
    List<Deuda> findByClienteZonaIdtbzonas(Long idzona);
    List<Deuda> findByPeriodoBetween(Date inicio, Date fin);

    @Query("SELECT d FROM Deuda d WHERE YEAR(d.periodo) = :year")
    List<Deuda> findAllByYear(@Param("year") int year);
}
