package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.lichChieu.LichChieuDto;
import com.demo.assignment.dto.ve.VeDto;

public interface QuanLyVeService {
    ResponseDto themLichChieu(LichChieuDto lichChieuDto);
    ResponseDto datVe(VeDto veDto);
    ResponseDto layDanhSachPhongVe(int maLichChieu);
}
