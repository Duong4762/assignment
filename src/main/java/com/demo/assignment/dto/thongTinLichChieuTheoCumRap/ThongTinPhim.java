package com.demo.assignment.dto.thongTinLichChieuTheoCumRap;


import lombok.Data;

import java.util.List;
@Data
public class ThongTinPhim {
    private String tenPhim;
    private String hinhAnh;
    private List<ThongTinLichChieu> danhSachLichChieu;
}
