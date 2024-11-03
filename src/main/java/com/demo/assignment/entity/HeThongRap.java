package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "he_thong_rap")
public class HeThongRap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "ma_he_thong_rap")
    private String maHeThongRap;
    @Column(name = "ten_he_thong_rap")
    private String tenHeThongRap;
    @Column(name = "bi_danh")
    private String biDanh;
    private String logo;

    @OneToMany(mappedBy = "heThongRap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CumRap> cumRaps;
}
