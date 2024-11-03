package com.demo.assignment.dto.ve;

import lombok.Data;

import java.util.List;

@Data
public class VeTheoThongTinNguoiDungDto {
    private List<GheTheoThongTinNguoiDungDto> danhSachGhe;
    private int maVe;
    private String ngayDat;
    private String tenPhim;
    private String hinhAnh;
    private double giaVe;
    private int thoiLuongPhim;
}
