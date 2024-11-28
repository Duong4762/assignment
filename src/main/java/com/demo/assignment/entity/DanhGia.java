package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "danh_gia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "so_sao")
    private int soSao;
    @Column(name = "nguoi_binh_luan")
    private String nguoiBinhLuan;
    @Column(name = "binh_luan")
    private String binhLuan;
    @Column(name = "ngay_gio_danh_gia", nullable = false)
    private LocalDateTime ngayGioDanhGia = LocalDateTime.now(); // Mặc định là thời gian hiện tại

    @ManyToOne
    @JoinColumn(name = "ma_phim")
    @JsonIgnore
    private Phim phim;
}
