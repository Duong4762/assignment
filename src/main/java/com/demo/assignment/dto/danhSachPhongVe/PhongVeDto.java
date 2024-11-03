package com.demo.assignment.dto.danhSachPhongVe;

import lombok.Data;

import java.util.List;

@Data
public class PhongVeDto {
    private ThongTinPhimTheoLichChieuDto thongTinPhim;
    private List<ThongTinGheTheoPhongVeDto> danhSachGhe;
}
