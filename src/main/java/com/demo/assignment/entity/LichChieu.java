package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "lich_chieu")
public class LichChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lich_chieu")
    private int maLichChieu;
    @Column(name = "ngay_chieu_gio_chieu")
//    ngayChieuGioChieu phải có định dạng dd/MM/yyyy hh:mm:ss
    private String ngayChieuGioChieu;
    @Column(name = "gia_ve")
    private double giaVe;

    @ManyToOne
    @JoinColumn(name = "ma_phim")
    private Phim phim;

    @OneToOne(mappedBy = "lichChieu", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PhongVe phongVe;

    @ManyToOne
    @JoinColumn(name = "ma_rap")
    @JsonBackReference
    private Rap rap;

    @OneToMany(mappedBy = "lichChieu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ve> ves;


    @Override
    public String toString() {
        return "LichChieu{" +
                "maLichChieu=" + maLichChieu +
                ", ngayChieuGioChieu='" + ngayChieuGioChieu + '\'' +
                ", maRap='" + rap.getMaRap() + '\'' +
                ", giaVe=" + giaVe +
                ", maPhim=" + phim.getMaPhim() +
                '}';
    }
}
