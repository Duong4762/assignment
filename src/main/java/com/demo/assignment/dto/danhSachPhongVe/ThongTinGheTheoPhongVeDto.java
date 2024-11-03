package com.demo.assignment.dto.danhSachPhongVe;

import lombok.Data;

@Data
public class ThongTinGheTheoPhongVeDto {
    private int maGhe;
    private String tenGhe;
    private int maRap;
    private String loaiGhe;
    private String stt;
    private double giaVe;
    private boolean daDat;
    private String taiKhoanNguoiDat;
}
