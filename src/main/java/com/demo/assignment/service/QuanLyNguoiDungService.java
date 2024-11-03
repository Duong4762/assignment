package com.demo.assignment.service;

import com.demo.assignment.dto.NguoiDung.NguoiDungDto;
import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.thongTinDangNhap.ThongTinDangNhap;

public interface QuanLyNguoiDungService {
    ResponseDto dangKy(NguoiDungDto nguoiDungDto);
    ResponseDto dangNhap(ThongTinDangNhap thongTinDangNhap);
    ResponseDto layThongTinTaiKhoan();
}
