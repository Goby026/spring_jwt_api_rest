package com.dev.pc.repository;

import com.dev.pc.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findByClienteNombresContaining(String nombres);

}
