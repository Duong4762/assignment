package com.demo.assignment.dto.thongTinLichChieuPhim;

import lombok.Data;

import java.util.List;
@Data
public class ThongTinLichChieuPhimDto {
    private List<HeThongRapChieuDto> heThongRapChieu;
    private int maPhim;
    private String tenPhim;
    private String biDanh;
    private String trailer;
    private String hinhAnh;
    private String moTa;
    private String maNhom;
    private boolean hot;
    private boolean dangChieu;
    private boolean sapChieu;
    private String ngayKhoiChieu;
    private double danhGia;
}
