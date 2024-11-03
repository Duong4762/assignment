package com.demo.assignment.dto.thongTinLichChieuHeThongRap;

import lombok.Data;

import java.util.List;
@Data
public class ThongTinLichChieuHeThongRapDto {
    private List<ThongTinLichChieuCumRapDto> lstCumRap;
    private String maHeThongRap;
    private String tenHeThongRap;
    private String logo;
    private String maNhom;
}
