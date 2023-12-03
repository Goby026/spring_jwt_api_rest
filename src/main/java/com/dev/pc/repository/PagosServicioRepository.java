package com.dev.pc.repository;

import com.dev.pc.models.PagosServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PagosServicioRepository extends JpaRepository<PagosServicio, Long> {
    List<PagosServicio> findByClienteIdclientes( Long id );
    List<PagosServicio> findByCajaIdcaja( Long idcaja );

    List<PagosServicio> findByTipoPagoServiciosIdtipopagosservicioAndCreatedAtBetween(Long idTributo, Date desde, Date hasta);

//    @Query("SELECT p FROM PagosServicio p WHERE p.fecha LIKE %?1%")
//    List<PagosServicio> findByFechaContaining(Date param);

    @Query(value = "SELECT * FROM jassunas.tbpagosservicios t WHERE t.fecha Like %?1%", nativeQuery = true)
    List<PagosServicio> findByMatchMonthAndMatchDay(String param);

    List<PagosServicio> findAllByCreatedAtBetween(
            Date publicationTimeStart,
            Date publicationTimeEnd);

    long count();
}
