package com.dev.pc.repository;

import com.dev.pc.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombresContaining(String nombre);
    List<Cliente> findByDniContaining(String dni);
    List<Cliente> findByApepaternoContaining(String ape);
    List<Cliente> findByZonaIdtbzonas(Long idzona);

    @Query("SELECT c FROM Cliente c WHERE c.apepaterno LIKE %?1% " +
            "OR c.apematerno LIKE %?1% " +
            "OR c.nombres LIKE %?1% " +
            "OR c.dni LIKE %?1%")
    List<Cliente> findAll(String clave);

//    @Query("SELECT c FROM Cliente c WHERE " +
//            "CONCAT(c.apepaterno, c.apematerno, c.nombres, c.dni) " +
//            "LIKE %?1%")
//    List<Cliente> findAll(String clave);
}
