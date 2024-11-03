package com.demo.assignment.dto.thongTinLichChieuPhim;

import lombok.Data;

import java.util.List;
@Data
public class CumRapChieuDto {
    private List<LichChieuPhimDto> lichChieuPhim;
    private String maCumRap;
    private String tenCumRap;
    private String hinhAnh;
    private String diaChi;
}
