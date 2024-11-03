package com.demo.assignment.repository;

import com.demo.assignment.entity.LichChieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LichChieuRepository extends JpaRepository<LichChieu, Integer> {
    List<LichChieu> findByPhim_MaNhom(String maNhom);
}
