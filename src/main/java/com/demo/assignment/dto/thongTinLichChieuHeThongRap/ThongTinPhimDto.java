package com.demo.assignment.dto.thongTinLichChieuHeThongRap;

import lombok.Data;

import java.util.List;
@Data
public class ThongTinPhimDto {
    private List<ThongTinLichChieuTheoPhimDto> lstLichChieuTheoPhim;
    private int maPhim;
    private String tenPhim;
    private String hinhAnh;
    private boolean hot;
    private boolean dangChieu;
    private boolean sapChieu;
}
