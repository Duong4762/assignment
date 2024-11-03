package com.demo.assignment.dto.thongTinLichChieuHeThongRap;

import lombok.Data;

import java.util.List;
@Data
public class ThongTinLichChieuCumRapDto {
    private List<ThongTinPhimDto> danhSachPhim;
    private String maCumRap;
    private String tenCumRap;
    private String hinhAnh;
    private String diaChi;
}
