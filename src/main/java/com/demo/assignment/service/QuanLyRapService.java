package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;

public interface QuanLyRapService {
    public ResponseDto layThongTinCumRapTheoHeThong(String maHeThongRap);
    public ResponseDto layThongTinLichChieuPhim(int maPhim);
    public ResponseDto layThongTinLichChieuHeThongRap(String maNhom);
}
