package com.demo.assignment.dto.thongTinDangNhap;

import lombok.Data;

@Data
public class ResponseDangNhap {
    private String taikhoan;
    private String hoTen;
    private String email;
    private String soDT;
    private String maNhom;
    private String maLoaiNguoiDung;
    private String accessToken;
}
