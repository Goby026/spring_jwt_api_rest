package com.dev.pc.repository;

import com.dev.pc.models.Ip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<Ip, Long> {
}
