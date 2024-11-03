package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "phim")
public class Phim {
    @Id
    @Column(name = "ma_phim")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int maPhim;
    @Column(name = "ten_phim")
    private String tenPhim;
    @Column(name = "bi_danh")
    private String biDanh;
    private String trailer;
    @Column(name = "hinh_anh")
    private String hinhAnh;
    @Column(name = "mo_ta", length = 5000)
    private String moTa;
    @Column(name = "ma_nhom")
    private String maNhom;
    @Column(name = "ngay_khoi_chieu")
    private String ngayKhoiChieu;
    @Column(name = "danh_gia")
    private double danhGia;
    private boolean hot;
    @Column(name = "dang_chieu")
    private boolean dangChieu;
    @Column(name = "sap_chieu")
    private boolean sapChieu;

    @OneToMany(mappedBy = "phim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LichChieu> lichChieus;

    @Override
    public String toString() {
        return "Phim{" +
                "maPhim=" + maPhim +
                ", tenPhim='" + tenPhim + '\'' +
                ", biDanh='" + biDanh + '\'' +
                ", trailer='" + trailer + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", moTa='" + moTa + '\'' +
                ", maNhom='" + maNhom + '\'' +
                ", ngayKhoiChieu='" + ngayKhoiChieu + '\'' +
                ", danhGia=" + danhGia +
                ", hot=" + hot +
                ", dangChieu=" + dangChieu +
                ", sapChieu=" + sapChieu +
                '}';
    }
}
