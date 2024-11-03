package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ve")
@Data
public class Ve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ngayDat;

    @ManyToOne
    @JoinColumn(name = "ma_lich_chieu")
    @JsonBackReference
    private LichChieu lichChieu;

    @OneToMany(mappedBy = "ve", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ghe> ghes;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    @JsonBackReference
    private NguoiDung nguoiDung;
}
