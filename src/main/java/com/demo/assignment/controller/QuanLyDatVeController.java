package com.demo.assignment.controller;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.lichChieu.LichChieuDto;
import com.demo.assignment.dto.ve.VeDto;
import com.demo.assignment.service.QuanLyVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/QuanLyDatVe")
public class QuanLyDatVeController {
    @Autowired
    private QuanLyVeService quanLyVeService;
    @PostMapping("/TaoLichChieu")
    public ResponseDto TaoLichChieu(@RequestBody LichChieuDto lichChieuDto) {
        return quanLyVeService.themLichChieu(lichChieuDto);
    }
    @PostMapping("/DatVe")
    public ResponseDto datVe(@RequestBody VeDto veDto){
        return quanLyVeService.datVe(veDto);
    }
    @GetMapping("/LayDanhSachPhongVe")
    public ResponseDto layDanhSachPhongVe(@RequestParam(name = "MaLichChieu") int maLichChieu){
        return quanLyVeService.layDanhSachPhongVe(maLichChieu);
    }
}
