package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.thongTinLichChieuTheoCumRap.ThongTinRap;

import java.util.List;

public interface QuanLyRapService {
    public ResponseDto layThongTinCumRapTheoHeThong(String maHeThongRap);
    public ResponseDto layThongTinLichChieuPhim(int maPhim);
    public ResponseDto layThongTinLichChieuHeThongRap(String maNhom);
    public List<ThongTinRap> layDanhSachLichChieuTheoCumRap(int idCumRap);
}
