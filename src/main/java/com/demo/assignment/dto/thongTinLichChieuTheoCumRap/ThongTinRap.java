package com.demo.assignment.dto.thongTinLichChieuTheoCumRap;

import lombok.Data;

import java.util.List;
@Data
public class ThongTinRap {
    private String tenRap;
    private List<ThongTinPhim> danhSachPhim;
}
