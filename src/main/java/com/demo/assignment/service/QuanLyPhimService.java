package com.demo.assignment.service;

import com.demo.assignment.dto.ResponseDto;
import com.demo.assignment.dto.phim.ThongTinSuaPhimDto;
import com.demo.assignment.dto.phim.ThongTinTaoPhimDto;
import com.demo.assignment.entity.Phim;

public interface QuanLyPhimService {
    ResponseDto getPhimByNameAndGroup(String name, String group);
    ResponseDto getPhimByPhimCode(int maPhim);
    ResponseDto deletePhim(int maPhim);
    ResponseDto addPhim(ThongTinTaoPhimDto phim);
    ResponseDto updatePhim(ThongTinSuaPhimDto phim);
}
