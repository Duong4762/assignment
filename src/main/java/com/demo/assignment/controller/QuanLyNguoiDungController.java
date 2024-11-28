package com.demo.assignment.controller;

import com.demo.assignment.dto.NguoiDung.NguoiDungDto;
import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.thongTinDangNhap.ThongTinDangNhap;
import com.demo.assignment.service.QuanLyNguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/QuanLyNguoiDung")
public class QuanLyNguoiDungController {

    @Autowired
    private QuanLyNguoiDungService quanLyNguoiDungService;

    @PostMapping("/DangNhap")
    public ResponseDto dangNhap(@RequestBody ThongTinDangNhap thongTinDangNhap){
        return quanLyNguoiDungService.dangNhap(thongTinDangNhap);
    }
    @PostMapping("/DangKy")
    public ResponseDto themNguoiDung(@RequestBody NguoiDungDto nguoiDungDto){
        return quanLyNguoiDungService.dangKy(nguoiDungDto);
    }
    @GetMapping("/ThongTinTaiKhoan")
    public ResponseDto layThongTinTaiKhoan(){
        return quanLyNguoiDungService.layThongTinTaiKhoan();
    }
}
