package com.demo.assignment.dto.thongTinLichChieuPhim;

import lombok.Data;

import java.util.List;
@Data
public class HeThongRapChieuDto {
    private List<CumRapChieuDto> cumRapChieu;
    private String maHeThongRap;
    private String tenHeThongRap;
    private String logo;
}
