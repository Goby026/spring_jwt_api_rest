package com.dev.pc.repository;

import com.dev.pc.models.PagosServiciosDeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PagosServiciosDetaRespository extends JpaRepository<PagosServiciosDeta, Long> {
    //buscador por cliente
    List<PagosServiciosDeta> findByClienteIdclientes(Long id);

    //buscar por cliente y año
    List<PagosServiciosDeta> findByClienteIdclientesAndIdanno(Long idcliente, int anio);

    //listar por id de PagosServicio
    List<PagosServiciosDeta> findByPagosServicioId(Long idpago);

    //listar por rango de fechas
    List<PagosServiciosDeta> findByCreatedAtBetween(Date desde, Date hasta);

}
