package com.demo.assignment.controller;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.service.QuanLyRapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/QuanLyRap")
public class QuanLyRapController {
    @Autowired
    private QuanLyRapService quanLyRapService;
    @GetMapping("/LayThongTinCumRapTheoHeThong")
    public ResponseDto layThongTinCumRapTheoHeThong(@RequestParam String maHeThongRap){
        return quanLyRapService.layThongTinCumRapTheoHeThong(maHeThongRap);
    }
    @GetMapping("/LayThongTinLichChieuPhim")
    public ResponseDto layThongTinLichChieuPhim(@RequestParam(defaultValue = "", required = false, name = "MaPhim") int maPhim){
        return quanLyRapService.layThongTinLichChieuPhim(maPhim);
    }
    @GetMapping("/LayThongTinLichChieuHeThongRap")
    public ResponseDto layThongTinLichChieuHeThongRap(@RequestParam(required = false, defaultValue = "GP01") String maNhom){
        return quanLyRapService.layThongTinLichChieuHeThongRap(maNhom);
    }
}
