package com.dev.pc.repository;

import com.dev.pc.models.VoucherDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherDetalleRepository extends JpaRepository<VoucherDetalle, Long> {
    List<VoucherDetalle> findByVoucherIdvoucher(Long idvoucher);
}
