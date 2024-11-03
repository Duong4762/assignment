package com.demo.assignment.dto.NguoiDung;

import com.demo.assignment.dto.ve.VeTheoThongTinNguoiDungDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponseThongTinNguoiDungDto {
    private String taiKhoan;
    private String matKhau;
    private String hoTen;
    private String email;
    private String soDT;
    private String maNhom;
    private String loaiNguoiDung;
    private List<VeTheoThongTinNguoiDungDto> thongTinDatVe;
}
