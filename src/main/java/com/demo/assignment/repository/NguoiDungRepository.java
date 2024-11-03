package com.demo.assignment.repository;

import com.demo.assignment.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    NguoiDung findByTaiKhoan(String taiKhoan);

}
