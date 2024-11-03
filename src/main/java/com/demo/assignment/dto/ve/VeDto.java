package com.demo.assignment.dto.ve;

import lombok.Data;

import java.util.List;

@Data
public class VeDto {
    private int maLichChieu;
    private List<GheDto> danhSachVe;
}
