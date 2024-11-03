package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ghe")
@Data
public class Ghe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ghe")
    private int maGhe;
    @Column(name = "ten_ghe")
    private String tenGhe;
    @Column(name = "loai_ghe")
    private String loaiGhe;
    private String stt;
    @Column(name = "da_dat")
    private boolean daDat;
    @Column(name = "tai_khoan_nguoi_dat")
    private String taiKhoanNguoiDat;

    @ManyToOne
    @JoinColumn(name = "ve_id")
    @JsonBackReference
    private Ve ve;

    @ManyToOne
    @JoinColumn(name = "phong_ve_id")
    @JsonBackReference
    private PhongVe phongVe;
}
