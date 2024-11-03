package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "rap")
public class Rap {
    @Id
    @Column(name = "ma_rap")
    private int maRap;
    @Column(name = "ten_rap")
    private String tenRap;

    @ManyToOne
    @JoinColumn(name = "cum_rap_id")
    @JsonBackReference
    private CumRap cumRap;

    @OneToMany(mappedBy = "rap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LichChieu> lichChieus;

}
