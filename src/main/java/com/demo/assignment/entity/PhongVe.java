package com.demo.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phong_ve")
@Data
public class PhongVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "lich_chieu_id", referencedColumnName = "ma_lich_chieu")
    @JsonBackReference
    private LichChieu lichChieu;

    @OneToMany(mappedBy = "phongVe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Ghe> danhSachGhe;

    public PhongVe() {
        creatDefaultSeats();
    }

    private void creatDefaultSeats() {
        List<Ghe> ghes = new ArrayList<>();
        for (int i=1; i<=160; i++){
            Ghe ghe = new Ghe();
            if(i<=9) ghe.setTenGhe(String.format("%02d", i));
            else ghe.setTenGhe(Integer.toString(i));
            ghe.setLoaiGhe("Thuong");
            if((i>=35&&i<=46)||(i>=51&&i<=62)||(i>=67&&i<=78)||(i>=83&&i<=94)||(i>=99&&i<=110)||(i>=115&&i<=126)) ghe.setLoaiGhe("Vip");
            ghe.setStt(ghe.getTenGhe());
            ghe.setDaDat(false);
            ghe.setTaiKhoanNguoiDat(null);
            ghe.setPhongVe(this);
            ghes.add(ghe);
        }
        this.setDanhSachGhe(ghes);
    }
}
