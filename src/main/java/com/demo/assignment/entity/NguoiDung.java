package com.demo.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tai_khoan", nullable = false, unique = true)
    private String taiKhoan;
    @Column(name = "mat_khau")
    private String matKhau;
    @Column(name = "ho_ten")
    private String hoTen;
    private String email;
    @Column(name = "so_DT")
    private String soDT;
    @Column(name = "ma_nhom")
    private String maNhom;
    @Column(name = "loai_nguoi_dung")
    private String loaiNguoiDung;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ve> ves;
}
