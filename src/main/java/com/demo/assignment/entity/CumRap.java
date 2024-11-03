package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cum_rap")
public class CumRap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "ma_cum_rap")
    private String maCumRap;
    @Column(name = "ten_cum_rap")
    private String tenCumRap;
    @Column(name = "dia_chi")
    private String diaChi;

    @OneToMany(mappedBy = "cumRap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Rap> danhSachRap;

    @ManyToOne
    @JoinColumn(name = "he_thong_rap_id")
    @JsonBackReference
    private HeThongRap heThongRap;
}
