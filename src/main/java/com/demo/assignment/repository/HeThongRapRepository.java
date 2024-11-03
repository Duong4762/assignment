package com.demo.assignment.repository;

import com.demo.assignment.entity.HeThongRap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeThongRapRepository extends JpaRepository<HeThongRap,Integer> {
    HeThongRap findByMaHeThongRap(String maHeThongRap);
}
